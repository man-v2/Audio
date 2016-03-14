<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#txt").change(function () {
                $.ajax({
                    url: "servlet/AudioServlet",
                    type: "POST",
                    data: {"txt": $(this).val()},
                    dataType: "text",
                    success: function (data) {
                        $("#as").append($("<a href='/Audio/audios/" + data + "'>" + data + "</a>"));
                    }
                });
            })
        })
    </script>
</head>
<body>
<input type="text" id="txt" name="txt"/>

<div id="as"></div>
</body>
</html>