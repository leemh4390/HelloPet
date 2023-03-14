$(function(){
    $('.reserve_div > table tr > td .btnReject').click(function(e){
        e.preventDefault();
        $('#popReject').addClass('on');
    });

    $('.btnClose').click(function(){                
        $(this).closest('.popup').removeClass('on');                
    });
});