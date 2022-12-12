<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
The message is: ${msg}
<#list list as str>this is ${str}.</#list>
<#list cars as car>
    ${car.name}: ${car.price}
</#list>
<#assign colours = ["red", "green", "blue", "yellow"]>
<#list colours as col>
    ${col}
</#list>
<#assign items = {"pens": 3, "cups": 2, "tables": 1}>

<#list items?values as v>
    ${v}
</#list>
<#list items?keys as k>
    ${k}
</#list>
<#assign value="\t\tweather\n\n">

<#compress>
    ${value}
    Today is a wonderful day.
    1 2   3       4     5
</#compress>
</body>
</html>