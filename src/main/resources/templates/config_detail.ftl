<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Call Details</title>
</head>

<body>
    <h3> Configuration Details </h3>

    <table class="call_tbl">
        <thead>
            <tr>
                <th> <div class="call_hd">Transfer Type</div> </th>
                <th> <div class="call_hd">Location Name</div> </th>
                <th> <div class="call_hd">Location Number</div> </th>
                <th> <div class="call_hd">Enabled</div> </th>
                <th> <div class="call_hd">Weight</div> </th>
            </tr>
        </thead>

        <tbody>
            <#list config_list as config>
                <tr>
                    <td>${config.transferTypeName}</td>
                    <td>${config.locationName}</td>
                    <td>${config.locationNumber}</td>
                    <td>${config.locationEnabled}</td>
                    <td>${config.locationWeight}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>
