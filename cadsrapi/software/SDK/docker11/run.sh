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

echo ant -buildfile build.xml build:system -DCADSR.DS.TNS.HOST=${CADSR_DS_TNS_HOST} -DCADSR.DS.TNS_PORT=${CADSR_DS_PORT} -DCADSR.DS.TNS.SID=${CADSR_DS_TNS_SID} -DCADSR.DS.USER=${CADSR_DS_USER} -DCADSR.DS.PSWD=${CADSR_DS_PSWD} -DFREESTYLE.DS.USER=${FREESTYLE_DS_USER} -DFREESTYLE.DS.PSWD=${FREESTYLE_DS_PSWD} -DTOOL.BASE.DIR=${TOOL_BASE_DIR} -Dlocal-transform=${TOOL_BASE_DIR}/cadsrapi/transform -DlogLevel=DEBUG -Dtiername=${tiername} -DJDEBUG=$debug -DTEST=false
ant -buildfile build.xml build:system -DCADSR.DS.TNS.HOST=${CADSR_DS_TNS_HOST} -DCADSR.DS.TNS.PORT=${CADSR_DS_TNS_PORT} -DCADSR.DS.TNS.SID=${CADSR_DS_TNS_SID} -DCADSR.DS.USER=${CADSR_DS_USER} -DCADSR.DS.PSWD=${CADSR_DS_PSWD} -DFREESTYLE.DS.USER=${FREESTYLE_DS_USER} -DFREESTYLE.DS.PSWD=${FREESTYLE_DS_PSWD} -DTOOL.BASE.DIR=${TOOL_BASE_DIR} -Dlocal-transform=${TOOL_BASE_DIR}/cadsrapi/transform -DlogLevel=DEBUG -Dtiername=${tiername} -DJDEBUG=$debug -DTEST=false


echo "=> starting wildfly in background"
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> deploying modules"
cp dist/cadsrapi41.ear /local/content/cadsrapi/bin
cp dist/ojdbc7.jar /local/content/cadsrapi/bin
cp dist/cadsrapi_modules.cli /local/content/cadsrapi/bin
cp dist/cadsrapi_setup_deploy.cli /local/content/cadsrapi/bin
cp dist/freestyle_autorun/*.* /local/content/freestyle/bin
chmod 755 /local/content/freestyle/bin/autorun.sh 


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
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 
