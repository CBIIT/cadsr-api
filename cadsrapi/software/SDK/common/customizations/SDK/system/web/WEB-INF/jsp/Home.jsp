<%--L
  Copyright Oracle Inc, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-api/LICENSE.txt for details.
L--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page
	import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.context.SecurityContextHolder"%>
<%@ page import="org.acegisecurity.userdetails.UserDetails"%>
<%@ page import="gov.nih.nci.system.web.util.JSPUtils"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
			String lastUserKey = (String) session
			.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY);
	if (lastUserKey == null || lastUserKey.equalsIgnoreCase("null")) {
		lastUserKey = "";
	}
	//out.println("lastUserKey: " + lastUserKey);

	String loginErrorStr = request.getParameter("login_error");
	boolean isLoginError = false;
	if (loginErrorStr != null && loginErrorStr.length() > 0) {
		isLoginError = true;
	}
	//out.println("isLoginError: " + isLoginError);
	JSPUtils jspUtils= JSPUtils.getJSPUtils(config.getServletContext());
	boolean isSecurityEnabled = jspUtils.isSecurityEnabled();

	boolean isAuthenticated = false;
	String userName = "";
	if (isSecurityEnabled){
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	
		//out.println("obj: " + obj);
		if (obj instanceof UserDetails) {
			userName = ((UserDetails) obj).getUsername();
		} else {
			userName = obj.toString();
		}
	
		if (userName != null
				&& !(userName.equalsIgnoreCase("anonymousUser"))) {
			isAuthenticated = true;
		}
	}
	//out.println("userName: " + userName);
	boolean webinterfaceDisabled=jspUtils.isWebInterfaceDisabled();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title><s:text name="home.title" />
		</title>
		<link rel="stylesheet" type="text/css" href="styleSheet.css" />
		<script src="script.js" type="text/javascript"></script>
	</head>
	<body>
		<table summary="" cellpadding="0" cellspacing="0" border="0"
			width="100%" height="100%">

			<%@ include file="include/header.inc"%>

			<tr>
				<td height="100%" align="center" valign="top">
					<table summary="" cellpadding="0" cellspacing="0" border="0"
						height="100%" width="771">

						<%@ include file="include/applicationHeader.inc"%>

						<tr>
							<td valign="top">
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									height="100%" width="100%">
									<tr>
										<td height="20" class="mainMenu">

											<%@ include file="include/mainMenu.inc"%>

										</td>
									</tr>

									<!--_____ main content begins _____-->
									<tr>
										<td valign="top">
											<!-- target of anchor to skip menus -->
											<a name="content" />
												<table summary="" cellpadding="0" cellspacing="0" border="0"
													class="contentPage" width="100%" height="100%">

													<!-- banner begins -->
													<tr>
														<td class="bannerHome">
															<img src="images/cadsrLogoLarge.gif" alt="caDSR Logo">
														</td>
													</tr>
													<!-- banner begins -->

													<tr>
														<td height="100%">

															<!-- target of anchor to skip menus -->
															<a name="content" />

																<table summary="" cellpadding="0" cellspacing="0"
																	border="0" height="100%">
																	<tr>
																		<td width="70%">

																			<!-- welcome begins -->
																			<table summary="" cellpadding="0" cellspacing="0"
																				border="0" height="100%">
																				<tr>
																					<td class="welcomeTitle" height="20">
																						Welcome to the caDSR API
																					</td>
																				</tr>
																				<tr>
																					<td class="welcomeContent" valign="top">
											caDSR API domain class browser includes three major components: caDSR domain model, Object Cart model and caDSR UML project model. In addition, Freestyle Search is part of caDSR API.<br><br>
											 
                                                                                        caDSR is the Cancer Data Standards Repository of Common Data Elements (CDEs), a database managed at NCI CBIIT. CDEs are used as data descriptors (metadata) for NCI-sponsored research.
                                                                                        <br>
                                                                                        <br>
CDEs are based on EVS thesauri and standard vocabularies and are developed by NCI CBIIT and partner organizations including participants in caBIG(tm). The caDSR tools are used to create, edit and deploy CDEs. caCORE application web services provide programmatic access to caDSR content.
                                                                                        <br>
                                                                                        <br>
caCORE objects are represented by UML Models. A UML Model is used to facilitate a semi-automated load from caCORE UML into ISO/IEC 11179 Administered Components. This is discussed in more detail on the caCORE SDK wiki page.
                                                                                        <br>
                                                                                        <br>
CDEs address a significant problem in biomedical data management--the many and varied ways that similar or identical concepts are described. CDEs provide consistent data descriptors (metadata), making it possible to aggregate and manage both modest-sized and large data sets in order to be able to ask and answer basic questions.
<br><br><b><u>caDSR domain model:</u></b>
This is the principal caDSR API, and it is based on the ISO 11179 information model as extended to capture caDSR semantics.
<br><br>
			<b><u>Object Cart model:</u></b> <br>
				The Object Cart application is a an application built on top of the caCORE SDK with the purpose of providing shopping cart type operations for data objects.  
			    The Object Cart provides a client API that client applications can utilize to create instance of object "carts" to be able to store, retrieve, update, and delete data objects.  
				Both Java objects adhering to the JavaBean specification and XML documents representing data objects can be passed to the client API.  

				The initial users of the Object Cart application are the CDE Browser and Forms Builder applications, allowing users to share their collections of objects 
				(Administered Components, CDEs, etc...) amongst the two applications while keeping the CDE Browser and Forms Builder decoupled.

<br><br><b><u>caDSR UML project model:</u></b><br>
This API presents a UML Model view of the underlying caDSR metadata. The API objects are mapped to a set of materialized views views in the underlying database.

			<br><br><b><u><a href="<%=basePath%>freestyle/" target="_blank">Freestyle Search:</a></u></b> <br>
				The caDSR Freestyle Search engine performs weighted semantic searches on the content of Administered Items in the caDSR. Freestyle Search is packaged as an API 
				and also includes a sample UI accessible from a web browser.
				
				The Administered Items searched by Freestyle Search are Data Element, Data Element Concept, Value Domain, Object Class, Property, Concept, Conceptual Domain and
				Value Meaning. The attributes searched on each are Version, Long Name, Preferred Name, Name, Preferred Definition, Question, Public ID, Latest Version Indicator,
				Created By, Modified By, Workflow Status, Registration Status, Context, Alternate Name, Definition Source and Origin.<br><br>
				
				To access the Freestyle Search UI, please <b><u><a href="<%=basePath%>freestyle/" target="_blank">click here.</a></u></b><br>
				To access the Wiki documentation and download the associated files for using Freestyle Search API, please <b><u><a href="https://wiki.nci.nih.gov/display/caDSR/caDSR+Freestyle+Search+FAQs#caDSRFreestyleSearchFAQs-HowistheFreestyleAPIused" target="_blank">click here.</a></u></b>


				<br>
				<br>
				References:
				<br>

				<ul>
					<li>
						<a  target="_blank"
							href="https://gforge.nci.nih.gov/projects/cadsrdb/">caDSR
							Database/API GForge site</a> - Contains news, information,
						documents, defects, feedback, and reports
					</li>
					<li>
<a  target="_blank"
							href="http://cbiit.nci.nih.gov/ncip/biomedical-informatics-resources/interoperability-and-semantics/metadata-and-models">caDSR
							Information Home</a> - Contains documents,
						information, and downloads for the caDSR
					</li>
					<li>
<a  target="_blank"
							href="https://wiki.nci.nih.gov/display/caDSR/caDSR+API+4.1+Release+Notes">caDSR
							4.1 Release Notes</a>
					</li>
					<li>
<a  target="_blank"
						    href="docs/index.html">caDSR 4.1
							javadocs</a>
					</li>
				</ul>
			</td>
		</tr>
																			
																			</table>
																			<!-- welcome ends -->


																		</td>
																		<td valign="top" width="30%">

																			<!-- sidebar begins -->
																			<table summary="" cellpadding="0" cellspacing="0"
																				border="0" height="100%">

																				<!-- login/continue form begins -->
																				<tr>
																					<td valign="top">
																						<%
																						if (webinterfaceDisabled) {
																						%>
																						<table summary="" cellpadding="2" cellspacing="0"
																							border="0" width="100%" class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									SELECT CRITERIA
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent" align="center">
																									WebInterface is Disabled
																								</td>
																							</tr>
																						</table>
																						<%
																						} else if (isSecurityEnabled && !isAuthenticated) {
																						%>

																						<table summary="" cellpadding="2" cellspacing="0"
																							border="0" width="100%" class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									<s:text name="home.login" />
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent">
																									<s:form method="post"
																										action="j_acegi_security_check"
																										name="loginForm" theme="css_xhtml">
																										<table cellpadding="2" cellspacing="0"
																											border="0">
																											<%
																											if (isLoginError) {
																											%>
																											<tr>
																												<td class="sidebarLogin" align="left"
																													colspan="2">
																													<font color="red"> Your login
																														attempt was not successful; please try
																														again.<BR> <BR> Reason: <%=((AuthenticationException) session
												.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY))
												.getMessage()%> <BR> <BR> </font>
																												</td>
																											</tr>
																											<%
																											}
																											%>

																											<tr>
																												<td class="sidebarLogin" align="left">
																													<s:text name="home.loginID" />
																												</td>
																												<td class="formFieldLogin">
																													<s:textfield name="j_username"
																														value="%{lastUserKey}"
																														cssClass="formField" size="14" />
																												</td>
																											</tr>
																											<tr>
																												<td class="sidebarLogin" align="left">
																													<s:text name="home.password" />
																												</td>
																												<td class="formFieldLogin">
																													<s:password name="j_password"
																														cssClass="formField" size="14" />
																												</td>
																											</tr>
																											<tr>
																												<td>
																													&nbsp;
																												</td>
																												<td>
																													<s:submit cssClass="actionButton"
																														type="submit" value="Login" />
																												</td>
																											</tr>
																										</table>
																									</s:form>
																								</td>
																							</tr>
																						</table>
																						<%
																						} else {
																						%>

																						<table summary="" cellpadding="2" cellspacing="0"
																							border="0" width="100%" class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									SELECT CRITERIA
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent" align="center">
																									<s:form method="post"
																										action="ShowDynamicTree.action"
																										name="continueForm" theme="simple">
																										<s:submit cssClass="actionButton"
																											value="Continue" theme="simple" />
																									</s:form>
																								</td>
																							</tr>
																						</table>
																						<%
																						}
																						%>
																					</td>
																				</tr>
																				<!-- login ends -->

																				<!-- what's new begins -->
																				<tr>
																					<td valign="top">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="100%" class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									WHAT'S NEW
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent">
																									<ul>
																										<li>
																											Merged caDSR API domain model with secure Object Cart
																										</li>

																										<li>
																											508 Compliance
																										</li>

																										
																									</ul>
																								</td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																				<!-- what's new ends -->

																				<!-- did you know? begins -->
																				<tr>
																					<td valign="top">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="100%" height="100%"
																							class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									DID YOU KNOW?
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent" valign="top">
																									&nbsp;
																								</td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																				<!-- did you know? ends -->

																				<!-- spacer cell begins (keep for dynamic expanding) -->
																				<tr>
																					<td valign="top" height="100%">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="100%" height="100%"
																							class="sidebarSection">
																							<tr>
																								<td class="sidebarContent" valign="top">
																									&nbsp;
																								</td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																				<!-- spacer cell ends -->

																			</table>
																			<!-- sidebar ends -->

																		</td>
																	</tr>
																</table>
														</td>
													</tr>
												</table>
										</td>
									</tr>
									<!--_____ main content ends _____-->

									<tr>
										<td height="20" class="footerMenu">

											<!-- application ftr begins -->
											<%@ include file="include/applicationFooter.inc"%>
											<!-- application ftr ends -->

										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>

					<%@ include file="include/footer.inc"%>

				</td>
			</tr>
		</table>
	</body>
</html>