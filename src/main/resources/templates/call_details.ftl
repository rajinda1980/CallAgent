<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Call Details</title>
</head>

<body>
    <h3> Call Details - To Date </h3>

    <table class="call_tbl">
        <thead>
            <tr>
                <th> <div class="call_hd">Transfer Name</div> </th>
                <th> <div class="call_hd">Created Date Time</div> </th>
                <th> <div class="call_hd">Location Name</div> </th>
                <th> <div class="call_hd">Location Number</div> </th>
                <th> <div class="call_hd">Enabled</div> </th>
                <th> <div class="call_hd">Weight</div> </th>
            </tr>
        </thead>

        <tbody>
            <#list call_list as call>
                <tr>
                    <td>${call.transferTypeName}</td>
                    <td>${call.createdDateTime}</td>
                    <td>${call.locationName}</td>
                    <td>${call.locationNumber}</td>
                    <td>${call.locationEnabled}</td>
                    <td>${call.locationWeight}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>