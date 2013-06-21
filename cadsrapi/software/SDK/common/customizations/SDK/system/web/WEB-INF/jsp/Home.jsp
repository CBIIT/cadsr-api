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
                                                                                        caDSR is the Cancer Data Standards Repository of Common Data Elements (CDEs), a database managed at NCICB. CDEs are used as data descriptors (metadata) for NCI-sponsored research.
                                                                                        <br>
                                                                                        <br>
CDEs are based on EVS thesauri and standard vocabularies and are developed by NCICB and partner organizations including participants in caBIG(tm). The caDSR tools are used to create, edit and deploy CDEs. caCORE application web services provide programmatic access to caDSR content.
                                                                                        <br>
                                                                                        <br>
caCORE objects are represented by UML Models. A UML Model is used to facilitate a semi-automated load from caCORE UML into ISO/IEC 11179 Administered Components. This is discussed in more detail on the caCORE SDK wiki page.
                                                                                        <br>
                                                                                        <br>
CDEs address a significant problem in biomedical data management--the many and varied ways that similar or identical concepts are described. CDEs provide consistent data descriptors (metadata), making it possible to aggregate and manage both modest-sized and large data sets in order to be able to ask and answer basic questions.
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
																									href="http://ncicb.nci.nih.gov/NCICB/infrastructure/cacore_overview/cadsr">caDSR
																									Information Home</a> - Contains documents,
																								information, and downloads for the caDSR
																							</li>
																							<li>
                                                                                                <a  target="_blank"
																									href="https://wiki.nci.nih.gov/display/caDSR/caDSR+API+4.0.1+Release+Notes">caDSR
																									4.0.1 Release Notes</a>
																							</li>
																							<li>
                                                                                                <a  target="_blank"
																								    href="docs/index.html">caDSR 4.0.1
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
																											508 Compliance
																										</li>

																										<li>
																											New User Interface
																										</li>
																										<li>
																											New hierarchical package/class browsing
																										</li>
																										<li>
																											New single-session authentication
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