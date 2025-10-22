<!DOCTYPE html>
<html>
<head>
    <style>
        .main { font-weight: bold; font-size: xx-large; background-color: tomato; }
        .table { color: blue; background-color: yellow; }
    </style>
    <title>Countries</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <h2>List of Countries</h2>
    <div class="main">
        <a href="/">Back</a>
    </div>
    <table class="table">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Language</th>
        </tr>
        <#list countries as country>
            <tr>
                <td>${country.id}</td>
                <td>${country.name}</td>
                <td>${country.language}</td>
            </tr>
        </#list>
    </table>
</body>
</html>