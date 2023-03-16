$(document).ready(function(){
    $('input[name=all]').click(function(){
        let allcheck = $(this).is(':checked');

        if(allcheck){
            $('input:checkbox').prop('checked',true);
        }else{
            $('input:checkbox').prop('checked',false);
        }
    });

    $('input:checkbox').click(function(){

        let length = $('.member_check:checked').length;

        if(length > 1){
            $('input[name=all]').prop('checked',true);
        }else{
            $('input[name=all]').prop('checked',false);
        }

    });
});