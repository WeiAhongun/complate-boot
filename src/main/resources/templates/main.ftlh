<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<style>
    h4{
        text-align: center;
        margin-top: 50px;
    }
    table{
        margin: 0 auto;
        width: 900px;
        text-align: center;
        border-collapse: collapse;
    }
    th,td{
        line-height: 35px;
        border: 1px solid #ccc;
    }
</style>
<body>

<h4>${adminName}: 您好</h4>
    <table>
        <tr>
            <th>id</th>
            <th>身份证</th>
            <th>电话</th>
            <th>部门</th>
            <th>昵称</th>
            <th>账号</th>
            <th>密码</th>
            <th>薪资</th>
        </tr>
        <tr>
            <td>${id}</td>
            <td>${adminCode}</td>
            <td>${adminPhone}</td>
            <td>${adminDept}</td>
            <td>${nickName}</td>
            <td>${adminAccount}</td>
            <td>123456</td>
            <td>${adminSalary}</td>
        </tr>
    </table>
</body>
</html>
