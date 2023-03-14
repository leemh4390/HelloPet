$(document).ready(function(){
    $('input[id=terms_next]').click(function(e){
		e.preventDefault();
		
        let check1 = $('input[name=terms_check]').is(':checked');
        let check2 = $('input[name=privacy_check]').is(':checked');

        if(check1 && check2){
			let type = $('input[name=type]').val();
			if(type == 'owner'){
				location.href="/HelloPet/member/register";
			}else if(type == 'medical'){
				location.href="/HelloPet/member/registerMedical";
			}
        }else{
            alert('동의체크 확인!');
        }
    });

    $('input[name=all]').click(function(){
        let allcheck = $(this).is(':checked');

        if(allcheck){
            $('input:checkbox').prop('checked',true);
        }else{
            $('input:checkbox').prop('checked',false);
        }
    });

    $('input:checkbox').click(function(){

        let length = $('#member_check:checked').length;

        if(length > 1){
            $('input[name=all]').prop('checked',true);
        }else{
            $('input[name=all]').prop('checked',false);
        }

    });
});