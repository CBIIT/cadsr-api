#~/bin/sh
echo "CADSR API in the directory: " $PWD
echo "tag: " $tag
echo "BRANCH_OR_TAG: " $BRANCH_OR_TAG
git pull
if [ $tag != 'origin/master'  ] && [ $tag != 'master' ]; then
#  git checkout tags/$tag
#this is for branch checkout for now
	echo "checkout of" $tag
	git checkout $tag
fi
git pull

# Function to check if wildfly is up #
function wait_for_server() {
  until `/opt/wildfly/bin/jboss-cli.sh -c --controller=localhost:9990 ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> build application"

echo ant -buildfile build.xml build:system -DTOOL_BASE_DIR=${TOOL_BASE_DIR} -Dlocal-transform=${TOOL_BASE_DIR}/cadsrapi/transform -DCADSR_DS_TNS_PORT=${CADSR_DS_TNS_PORT} -DlogLevel=DEBUG -DFREESTYLE_DS_USER=${FREESTYLE_DS_USER} -DCADSR_DS_USER=${CADSR_DS_USER} -Dtiername=${tiername} -DCADSR_DS_TNS_SID=${CADSR_DS_TNS_SID} -DTEST=false -DCADSR_DS_TNS_HOST=${CADSR_DS_TNS_HOST} -DJDEBUG=off -DFREESTYLE_DS_PSWD=${FREESTYLE_DS_PSWD} -DCADSR_DS_PSWD=${CADSR_DS_PSWD}
ant -buildfile build.xml build:system -DTOOL_BASE_DIR=${TOOL_BASE_DIR} -Dlocal-transform=${TOOL_BASE_DIR}/cadsrapi/transform -DCADSR_DS_TNS_PORT=${CADSR_DS_TNS_PORT} -DlogLevel=DEBUG -DFREESTYLE_DS_USER=${FREESTYLE_DS_USER} -DCADSR_DS_USER=${CADSR_DS_USER} -Dtiername=${tiername} -DCADSR_DS_TNS_SID=${CADSR_DS_TNS_SID} -DTEST=false -DCADSR_DS_TNS_HOST=${CADSR_DS_TNS_HOST} -DJDEBUG=off -DFREESTYLE_DS_PSWD=${FREESTYLE_DS_PSWD} -DCADSR_DS_PSWD=${CADSR_DS_PSWD}


echo "=> starting wildfly in background"
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> deploying modules"
cp dist/cadsrapi41.ear /local/content/cadsrapi/bin
cp dist/ojdbc6.jar /local/content/cadsrapi/bin
cp dist/cadsrapi_modules.cli /local/content/cadsrapi/bin
cp dist/cadsrapi_setup_deploy.cli /local/content/cadsrapi/bin
cp dist/cadsrutil.properties /local/content/cadsrapi/bin
cp dist/transform.properties /local/content/cadsrapi/transform/config
cp dist/cdebrowser.xslt /local/content/cadsrapi/transform/xslt
cp dist/ConvertCDE.xslt /local/content/cadsrapi/transform/xslt
cp dist/formbuilder.xslt /local/content/cadsrapi/transform/xslt


/opt/wildfly/bin/jboss-cli.sh -c --controller=localhost:9990 --file=/local/content/cadsrapi/bin/cadsrapi_modules.cli

echo "=> reloading wildfly"
/opt/wildfly/bin/jboss-cli.sh --connect command=:reload

echo "=> Waiting for the server to reload"
wait_for_server

echo "=> deploying"
/opt/wildfly/bin/jboss-cli.sh -c --controller=localhost:9990 --file=/local/content/cadsrapi/bin/cadsrapi_setup_deploy.cli

echo "=> shutting wildfly down"
/opt/wildfly/bin/jboss-cli.sh --connect command=:shutdown

echo "=> starting wildfly in foreground"