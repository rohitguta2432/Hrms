<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>Welcome to SoftAge HRMS</title>
    <meta name="description" content="User login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="resources/libs/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/libs/css/font-awesome.css" />
    <link rel="stylesheet" href="resources/libs/css/ace.css" />
    <link rel="stylesheet" href="resources/libs/css/ace-rtl.css" />
    <script src="resources/libs/jquery/dist/jquery-ui.min.js"></script> 
</head>
<body class="login-layout light-login">
	<%session.invalidate(); %>
    <!-- /.navbar-container -->
    <div class="main-container" >
        <div class="main-content">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1">
                    <div class="login-container">

                        <div style="clear: both; height: 70px;">
                        </div>
                        <div class="space-6"></div>
                        <div class="position-relative">
                            <p style="text-align: center;">
                                <img src="resources/libs/images/hrms_logo.png" alt="SoftAge HRMS" style="" />
                            </p>
                            <div id="login-box" class="login-box visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="ace-icon fa fa-desktop blue"></i>
                                            Login Here
                                        </h4>
                                        <div class="space-6"></div>
                                        <c:url var="checkLogin" value="/checkLogin"></c:url>
                                        <form:form method="post" action="${checkLogin}" commandName="loginBean">
                                            <fieldset>
                                                <label class="block clearfix">
                                                    <span class="block input-icon input-icon-right">
                                                        <form:input type="text" path="exEmpUserid" class="form-control" placeholder="Username"  oninvalid="this.setCustomValidity('Enter User name')" 
                                                        oninput="setCustomValidity('')" />
                                                        <i class="ace-icon fa fa-user"></i>
                                                    </span>
                                                </label>
                                                <label class="block clearfix">
                                                    <span class="block input-icon input-icon-right">
                                                        <form:input type="password" class="form-control" placeholder="Password" oninvalid="this.setCustomValidity('Enter Password')" 
                                                        oninput="setCustomValidity('')" path="exEmpPassword"/>
                                                        <i class="ace-icon fa fa-lock"></i>
                                                    </span>
                                                </label>
                                                <div class="space"></div>
                                                <div class="clearfix">
                                                    <label class="inline">
                                                        <input class="ace" type="checkbox" />
                                                        <span class="lbl">Remember Me</span>
                                                    </label>

                                                    <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                                                        <i class="ace-icon fa fa-key"></i>
                                                        <span class="bigger-110">Submit</span>
                                                    </button>
                                                </div>
                                                <div class="space-4"></div>
                                            </fieldset>
                                        </form:form>
                                    </div>
                                    <!-- /.widget-main -->
                                    <div class="toolbar clearfix">
                                        <div style="width: 100%;">
                                            <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                                <i class="ace-icon fa fa-lock"></i>
                                                Forgot Password
                                            </a>
                                        </div>

                                    </div>
                                </div>
                                <!-- /.widget-body -->
                            </div>
                            <!-- /.login-box -->
                            <!-- /.forgetpassword-box -->
                            <div class="forgot-box widget-box no-border" id="forgot-box">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="ace-icon fa fa-key"></i>
                                            Retrieve Password
											</h4>

                                        <div class="space-6"></div>
                                        <p>
                                            Enter your email and to receive instructions
										
                                        </p>
                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
                                                    <span class="block input-icon input-icon-right">
                                                        <input type="email" placeholder="Email" class="form-control" />
                                                        <i class="ace-icon fa fa-envelope"></i>
                                                    </span>
                                                </label>

                                                <div class="clearfix">
                                                    <input type="submit" class="width-35 pull-right btn btn-sm btn-primary" value="SUBMIT" required oninvalid="this.setCustomValidity('Enter email')" oninput="setCustomValidity('')" />
<!--                                                     <button class="width-35 pull-right btn btn-sm btn-primary" type="submit" required oninvalid="this.setCustomValidity('Enter email')" oninput="setCustomValidity('')">
                                                        <i class="ace-icon fa fa-lightbulb-o"></i>
                                                        
                                                        <span class="bigger-110">Send Me!</span>
                                                    </button> -->
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                    <!-- /.widget-main -->

                                    <div class="toolbar center">
                                        <a class="back-to-login-link" data-target="#login-box" href="#">Back to login
											
                                            <i class="ace-icon fa fa-arrow-right"></i>
                                        </a>
                                    </div>
                                </div>
                                <!-- /.widget-body -->
                            </div>

                        </div>
                        <!-- /.position-relative -->
                    </div>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.main-content -->
    </div>
    <!-- /.main-container -->



    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
                <span class="bigger-120">
                    <img src="resources/libs/images/logo_softage.png" alt="SoftAge" style="width: 90px; margin-top: -5px;" />
                    <span class="white">HRMS </span>
                    &copy; 2016
                </span>

            </div>
        </div>
    </div>


    <!-- basic scripts -->
    <script src="resources/libs/js/jquery.min.js" type="text/javascript"></script>
    <!-- inline scripts related to this page -->
    <script type="text/javascript">
        jQuery(function ($) {
            $(document).on('click', '.toolbar a[data-target]', function (e) {
                e.preventDefault();
                var target = $(this).data('target');
                $('.widget-box.visible').removeClass('visible'); //hide others
                $(target).addClass('visible'); //show target
            });
        });

        //you don't need this, just used for changing background
        jQuery(function ($) {
            $('#btn-login-dark').on('click', function (e) {
                $('body').attr('class', 'login-layout');
                $('#id-text2').attr('class', 'white');
                $('#id-company-text').attr('class', 'blue');

                e.preventDefault();
            });
            $('#btn-login-light').on('click', function (e) {
                $('body').attr('class', 'login-layout light-login');
                $('#id-text2').attr('class', 'grey');
                $('#id-company-text').attr('class', 'blue');

                e.preventDefault();
            });
            $('#btn-login-blur').on('click', function (e) {
                $('body').attr('class', 'login-layout blur-login');
                $('#id-text2').attr('class', 'white');
                $('#id-company-text').attr('class', 'light-blue');

                e.preventDefault();
            });

        });
    </script>
</body>
</html>