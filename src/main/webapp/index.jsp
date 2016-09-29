<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quartz</title>
<style type="text/css">
.css {
	font-size: 14px;
	width: 100%;
	text-align: center;
}
</style>
</head>
<body>
	<div class="css">
		<fieldset>
			<legend>添加任务：</legend>
			<form action="<%=path%>/job/addJob.do" method="post">
				job_id:<input name="job_id"><br> 
				dto.job_name:<input name="job_name"><br> 
				dto.job_group:<input name="job_group"><br> 
				dto.job_time:<input name="job_time"><br> 
				<input type="submit">
			</form>
		</fieldset>
	</div>
	<div class="css">
		<fieldset>
			<legend>删除任务：</legend>
			<form action="<%=path%>/job/removeJob.do" method="post">
				dto.job_name:<input name="job_name"><br> 
				dto.job_group:<input name="job_group"><br> 
				<input type="submit">
			</form>
		</fieldset>
	</div>
	<div class="css">
		<fieldset>
			<legend>暂停任务：</legend>
			<form action="<%=path%>/job/pauseJob.do" method="post">
				dto.job_name:<input name="job_name"><br> 
				dto.job_group:<input name="job_group"><br> 
				<input type="submit">
			</form>
		</fieldset>
	</div>
	<div class="css">
		<fieldset>
			<legend>重启任务：</legend>
			<form action="<%=path%>/job/resumeJob.do" method="post">
				dto.job_name:<input name="job_name"><br> 
				dto.job_group:<input name="job_group"><br> 
				<input type="submit">
			</form>
		</fieldset>
	</div>
	<div class="css">
		<fieldset>
			<legend>暂停所有任务：</legend>
			<form action="<%=path%>/job/pauseJobs.do" method="post">
				<input type="submit">
			</form>
		</fieldset>
	</div>
	<div class="css">
		<fieldset>
			<legend>重启所有任务：</legend>
			<form action="<%=path%>/job/resumeJobs.do" method="post">
				<input type="submit">
			</form>
		</fieldset>
	</div>
</body>
</html>