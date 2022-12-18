
<#list gradList as gra>
    <#if  gra.id == "999990006">
        {
        "key":"父级${gra.code}",
        "value":"${gra.value}"
        }
    <#elseif gra.id?starts_with("999990006")>
        {
        "key":"下属分级${gra.code}_${gradMap[gra.id?substring("999990006"?length)]}",
        "value":"${gra.value}"
        }
    </#if>
    <#sep>, </#sep>
</#list>

