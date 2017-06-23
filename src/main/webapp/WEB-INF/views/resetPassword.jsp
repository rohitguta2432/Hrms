<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.tempuri.*" %>
<%@ page import="org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="resetPassword">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>Welcome to SoftAge HRMS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="../resources/libs/css/bootstrap.css"/>
    <link rel="stylesheet" href="../resources/libs/css/font-awesome.css"/>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">


    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
    <!-- ace styles -->
    <link rel="stylesheet" href="../resources/libs/css/ace.css" id="main-ace-style"/>
    <!-- ace settings handler -->

    <link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.css'
          type='text/css' media='all'/>


    <script src="../resources/libs/js/jquery.min.js"></script>
    <script src="../resources/libs/js/ace-extra.js"></script>
    <link href="../resources/libs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="../resources/libs/angular.min.js"></script>
    <script src="../resources/libs/angular-ui-router.min.js"></script>
    <!-- <script src="resources/libs/jquery/dist/jquery.min.js"></script> -->
    <!-- <script src="resources/libs/bootstrap/dist/js/bootstrap.min.js"></script> -->


    <script src="../resources/js/resetPwd.js"></script>

    <script type='text/javascript'
            src='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.js'></script>
    <style type="text/css">
        body {
            overflow: hidden;
        }
        .big-btn{
            padding-left:10%;
            padding-right:10%;
        }
        .form-horizontal input[type="password"]{
            border-radius:3px !important;
        }
    </style>
    <style>
        html.md-default-theme, html, body.md-default-theme, body {
            background-color: unset !important;
        }
    </style>
</head>
<body ng-controller="resetController" class="no-skin">
<div class="main-container" id="main-container"
     style="margin-top: 10px;">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>
    <div class="main-content">

        <div class="page-content">
            <div class="page-content-area">
                <!-- /.page-header -->
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-12 widget-container-col ui-sortable">
                                    <div class="widget-box ui-sortable-handle"
                                         style="opacity: 1; z-index: 0;">
                                        <div class="widget-header">
                                            <h5 class="widget-title">Reset Password</h5>

                                        </div>
                                        <form name="register" ng-submit="submit()" novalidate>
                                            <div class="widget-body" style="display: block;">
                                                <div class="widget-main form-horizontal">

                                                    <div class="clearfix"></div>

                                                    <label class="col-sm-3 control-label no-padding-right"
                                                           for="employeeepassword">Employee Password </label>

                                                    <div class="col-sm-9">
                                                        <input type="password" class="form-control col-xs-10 col-sm-12"
                                                               name="user_password" id="employeeepassword"
                                                               ng-model="password" ng-pattern="/^[a-zA-Z0-9]*$/" placeholder='Enter Password'
                                                               required="true">
                                                              
                                                        <input type="hidden" ng-model="uniqueID" ng-init="uniqueID='${uniqueID}'">
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>

                                                <div class="widget-main form-horizontal">
                                                    <label class="col-sm-3 control-label no-padding-right"
                                                           for="confirmpassword">Confirm Password </label>

                                                    <div class="col-sm-9">
                                                        <input type="password" class="form-control col-xs-10 col-sm-12"
                                                               name='user_password' id="confirmpassword" 
                                                               ng-model="passwordverify" ng-pattern="/^[a-zA-Z0-9]*$/" placeholder='Confirm Password'
                                                               ng-required="true"/>
                                                              
                                                    </div>
                                                </div>


                                                <div class="clearfix"></div>

                                                <div class="center layout-padding">
                                                    <input type="submit" value="Submit" class="btn btn-primary btn-sm"
                                                           ng-disabled="register.$invalid || password!==passwordverify">
                                                    <!-- register.$invalid &&  -->
                                                </div>
                                            </div>
                                    </form>
                                </div>
                            </div>
                        </div>


                    </div>
                    <!-- /.row -->
                    <!-- PAGE CONTENT ENDS -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.page-content-area -->
    </div>
    <!-- /.page-content -->
</div>
<a href="#" id="btn-scroll-up"
   class="btn-scroll-up btn btn-sm btn-inverse"><i
        class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a>
</div>
<!-- /.main-container -->
</body>
</html>