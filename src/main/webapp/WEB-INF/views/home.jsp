<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*"  %>
<%@ page import="org.tempuri.*" %>
<%@ page import="org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.*" %>
<!DOCTYPE html>
<html lang="en" ng-app="home">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>Welcome to SoftAge HRMS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="resources/libs/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/libs/css/font-awesome.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="resources/libs/css/ace.css" id="main-ace-style" />
    <!-- ace settings handler -->
    <script src="resources/libs/js/jquery.min.js"></script>

    <script src="resources/libs/js/ace-extra.js"></script>
    <link href="resources/libs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="resources/libs/angular.min.js" ></script>
	<script src="resources/libs/angular-ui-router.min.js"></script>
	<!-- <script src="resources/libs/jquery/dist/jquery.min.js"></script> -->
	<!-- <script src="resources/libs/bootstrap/dist/js/bootstrap.min.js"></script> -->
	 <script src="resources/libs/jquery/dist/jquery-ui.min.js"></script> 
	<script src="resources/libs/ui-bootstrap-tpls-0.14.3.min.js"></script>
	<script src="resources/js/home.js"></script>
	<script src="resources/js/rmapproval/approval.js"></script>
	<!-- <script src="resources/js/approvalActionModal.js"></script> -->
	<script src="resources/js/resignation/resignation.js"></script>
	<script src="resources/js/dowmloadDocument/download.js"></script>
	<script src="resources/js/document/upload.js"></script>

	<script src="resources/js/empfeedback/exitemprate.js"></script>
	<script src="resources/js/exitrm.js"></script>
	<script src="resources/js/empfeedback/exitemp.js"></script>
	<script src="resources/js/hrfeedback/exitemphr.js"></script>
	<script src="resources/js/hrfeedback/exithrmodal.js"></script>  
	<script src="resources/js/hrapproval/hr_approval.js"></script>
	<script src="resources/js/noduesit/noduesit.js"></script>
	<script src="resources/js/noduesit/noduesitmodal.js"></script>
	<script src="resources/js/noduesaccounts/noduesaccounts.js"></script>
	<script src="resources/js/noduesaccounts/noduesaccountsmodal.js"></script>
	<script src="resources/js/nodueshr/nodueshr.js"></script>
	<script src="resources/js/nodueshr/nodueshrmodal.js"></script>
	<script src="resources/js/noduesrm/noduesrm.js"></script>
	<script src="resources/js/noduesrm/noduesrmmodal.js"></script>
	<script src="resources/js/noduesinfra/noduesinfra.js"></script>
	<script src="resources/js/noduesinfra/noduesinframodal.js"></script>
</head>
<body class="no-skin" ng-controller="homeController" ng-init="getLinks()">
    <div id="navbar" class="navbar navbar-default navbar-collapse h-navbar">
        <script type="text/javascript">
            try { ace.settings.check('navbar', 'fixed') } catch (e) { }
        </script>
        <div class="navbar-container" id="navbar-container">
            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
                <span class="sr-only">Toggle sidebar</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="navbar-header pull-left">
                <a href="index.htm" class="navbar-brand"><small>                  
                    <img src="resources/libs/images/hrms_logo_s.png" alt="HRMS" style="height: 50px;" />
                </small></a>
            </div>
            <div class="navbar-buttons navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav">
                    <li class="light-blue"><a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="resources/libs/images/avatar2.png" alt="user" />
                        <span class="user-info"><small>Welcome,</small> <%= session.getAttribute( "firstname" ) %> </span><i class="ace-icon fa fa-caret-down"></i></a>
                        <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-blue dropdown-caret dropdown-close">
                            <li><a href="#" data-toggle="modal" data-target="#myModal1"><i class="ace-icon fa fa-user"></i>Change Password </a></li>
                            <li class="divider"></li>
                            <li><a href="./"><i class="ace-icon fa fa-power-off"></i>Logout </a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /.navbar-container -->
    </div>
    <div class="main-container" id="main-container" style="margin-top: 10px;">
        <script type="text/javascript">
            try { ace.settings.check('main-container', 'fixed') } catch (e) { }
        </script>

        <div id="sidebar" class="sidebar responsive" data-sidebar="true" data-sidebar-scroll="true"
            data-sidebar-hover="true" ng-model="pageList">

            <script type="text/javascript">
                try { ace.settings.check('sidebar', 'fixed') } catch (e) { }
            </script>

            <ul class="nav nav-list" style="top: 0px;">
            <li class="">
                    <a href="#" class="dropdown-toggle"><i class="menu-icon fa fa-list"></i>
                        <span class="menu-text">Exit Module </span><b class="arrow fa fa-angle-down"></b></a><b
                            class="arrow"></b>
                    <ul class="submenu nav-hide" style="display: none;">
                        <li class="hover" ng-repeat="page in pageList"><a ui-sref="{{page}}"><i class="menu-icon fa fa-caret-right"></i>{{page}} </a><b
                            class="arrow"></b></li>
                    </ul>
                </li>
<!--                 <li class="active"><a href="#"><i class="menu-icon fa fa-desktop"></i><span
                    class="menu-text">Home </span></a></li>
                    <li class="" ng-repeat="page in pageList">
                    <a ui-sref="{{page}}"><i class="menu-icon fa fa-pencil-square-o"></i><span class="menu-text">{{page}} </span></a></li> -->
                <!-- <li class="">
                    <a href="#" class="dropdown-toggle"><i class="menu-icon fa fa-list"></i>
                        <span class="menu-text">Admin </span><b class="arrow fa fa-angle-down"></b></a><b
                            class="arrow"></b>
                    <ul class="submenu nav-hide" style="display: none;">
                        <li class="hover"><a href="#"><i class="menu-icon fa fa-caret-right"></i>Heading 1 </a><b
                            class="arrow"></b></li>
                        <li class="hover"><a href="#"><i class="menu-icon fa fa-caret-right"></i>Heading 2</a><b
                            class="arrow"></b></li>
                    </ul>
                </li>
                <li class="">
                    <a href="#" class="dropdown-toggle">
                        <i class="menu-icon fa fa-user"></i>
                        <span class="menu-text">Human Resources </span><b class="arrow fa fa-angle-down"></b></a><b class="arrow"></b>
                    <ul class="submenu nav-hide" style="display: none;">
                        <li class=""><a href="#"><i class="menu-icon fa fa-caret-right"></i>Heading 1 </a><b
                            class="arrow"></b></li>
                        <li class=""><a href="#"><i class="menu-icon fa fa-caret-right"></i>Heading 2</a><b
                            class="arrow"></b></li>
                    </ul>
                </li>
                <li class="">
                    <a href="data_entrypage.htm"><i class="menu-icon fa fa-pencil-square-o"></i><span class="menu-text">Data Entry </span></a></li>
                <li><a href="#" data-toggle="modal" data-target="#myModal"><i class="menu-icon fa fa-list-alt"></i><span class="menu-text">Company
                    Policies </span></a></li>
                <li class=""><a href="report.htm"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">Report </span></a></li> --> 
            </ul>
            <!-- /.nav-list -->
            <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
                    data-icon2="ace-icon fa fa-angle-double-right"></i>
            </div>
            <script type="text/javascript">
                try { ace.settings.check('sidebar', 'collapsed') } catch (e) { }
            </script>
        </div>



        <div class="main-content" ui-view>
            <div class="page-content">
                <div class="page-content-area">
                    <!-- /.page-header -->
                    <div class="row">
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="space-14"></div>
                                    <p style="text-align: center;">
                                        <img src="resources/libs/images/hrms-img.png" alt="HRMS" />
                                    </p>
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
        <!-- /.main-content -->
        <div class="footer">
            <div class="footer-inner">
                <div class="footer-content">
                    <span class="bigger-120">
                        <img src="resources/libs/images/logo_softage.png" alt="SoftAge" style="width: 90px; margin-top: -5px;" />
                        <span class="white">HRMS </span>&copy; 2016 </span>
                </div>
            </div>
        </div>
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"><i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a>
    </div>
    <!-- /.main-container -->

    <!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body">
        <p>Some text in the modal.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
     <!--end Modal -->


    <!-- basic scripts -->
    <script src="resources/libs/js/bootstrap.js"></script>
    <!-- page specific plugin scripts -->
    <script src="resources/libs/js/jquery-ui.custom.min.js"></script>
    <!--<script src="js/jquery.ui.touch-punch.min.js"></script>-->
    <!-- ace scripts -->
    <!--<script src="js/ace-elements.js"></script>-->
    <script src="resources/libs/js/ace.js"></script>
    <!-- inline scripts related to this page -->


</body>
</html>