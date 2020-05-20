/******************************************
 * My Login
 *
 * Bootstrap 4 Login Page
 *
 * @author          Muhamad Nauval Azhar
 * @uri 			https://nauval.in
 * @copyright       Copyright (c) 2018 Muhamad Nauval Azhar
 * @license         My Login is licensed under the MIT license.
 * @github          https://github.com/nauvalazhar/my-login
 * @version         1.2.0
 *
 * Help me to keep this project alive
 * https://www.buymeacoffee.com/mhdnauvalazhar
 * 
 ******************************************/

'use strict';



$(function() {
	var password_different = 1;
	// author badge :)	
	$("input[type='password'][data-eye]").each(function(i) {
		var $this = $(this),
			id = 'eye-password-' + i,
			el = $('#' + id);
		$this.wrap($("<div/>", {//부모요소를 div/	로 감싸준다. 
			style: 'position:relative',
			id: id
		}));
		$this.css({
			paddingRight: 60
		});
		$this.after($("<div/>", {
			html: 'Show',
			class: 'btn btn-primary btn-sm',
			id: 'passeye-toggle-'+i,
		}).css({
				position: 'absolute',
				right: 10,
				top: ($this.outerHeight() / 2) - 12,
				padding: '2px 7px',
				fontSize: 12,
				cursor: 'pointer',
		}));

		$this.after($("<input/>", {
			type: 'hidden',
			id: 'passeye-' + i
		}));
		
		var invalid_feedback = $this.parent().parent().find('.invalid-feedback');//비밀번호 잘 못 치면 잘 못 쳤다고 출력하는 부분 
		if(invalid_feedback.length) {
			$this.after(invalid_feedback.clone());
		}
		$this.on("keyup paste", function() {
			$("#passeye-"+i).val($(this).val());
		});
		$("#passeye-toggle-"+i).on("click", function() {
			if($this.hasClass("show")) {
				$this.attr('type', 'password');
				$this.removeClass("show");
				$(this).removeClass("btn-outline-primary");
			}else{
				$this.attr('type', 'text');
				$this.val($("#passeye-"+i).val());				
				$this.addClass("show");
				$(this).addClass("btn-outline-primary");
			}
		});
	});
	
    $('.password-check').focusout(function () {
        var pwd1 = $("#password1").val();
        var pwd2 = $("#password2").val();
 
        if ( pwd1 != '' && pwd2 == '' ) {
            null;
        } else if (pwd1 != "" || pwd2 != "") {
            if (pwd1 == pwd2) {
                // 비밀번호 일치 이벤트 실행
            	password_different = 0;
            	$("#passwordDifferent").html("");
            	$('form').submit(true);//form을 가능하게 만든다. 

            	//div로 비밀번호 불일치를 없애준다. 
            } else {
            	alert("비밀번호가 다릅니다.");
            	password_different = 1;
            	$("#passwordDifferent").html("비밀번호 불일치");
            	$("#passwordDifferent").css("color", "red");

            	//div로 뿌려준다. 
            	$('form').submit(false);//form을 submit 불가능하게 만든다. 

            }
        }
    });
    
		$(".my-login-validation").submit(function() {
	        var pwd1 = $("#password1").val();
	        var pwd2 = $("#password2").val();
	    	var form = $(this);//
	        if (form[0].checkValidity() === false) {
	              event.preventDefault();// 고유 동작 중지 시킨다. 
	              event.stopPropagation();// 상위 엘리먼트로의 이벤트 전파 중단 
	        }	
	    	form.addClass('was-validated');//버튼 클릭후 클래스를 더해준다. 
		});
	
});
