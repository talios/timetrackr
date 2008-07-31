<html>
    <head>
        <title>Welcome!</title>
    </head>

    <body>
        <p>Your times at ${reportForDate} total ${formattedTimeFor}, and are broken down as:</p>

        <ul>
            <#list activities as each>
            <#if each.timeFor != 0>
            <li>
                <p><b>${each.activity.name} - ${each.formattedTimeFor}.</b></p>
                <#list each.workItems as item>
                <p>${item.comment}</p>
            </#list>
        </li>
    </#if>
</#list>
        </ul>
        </body>
        </html>
