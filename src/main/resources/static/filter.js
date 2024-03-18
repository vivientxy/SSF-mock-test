$(document).ready(function() {
    $('#filterDropdown').change(function() {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/filterData?selectedValue=' + selectedValue,
            success: function(data) {
                $('#dataTable tbody').empty();
                $.each(data, function(index, row) {
                    $('#dataTable tbody').append('<tr><td>' + row.column1 + '</td><td>' + row.column2 + '</td></tr>');
                    // Add more columns if needed
                });
            }
        });
    });
});
