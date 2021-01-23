function changePage(num) {
    if (num === 0) {
        $("#useLogin").show();
        $("#useRegister").hide();
        $("#updatePwd").hide()
        $("#message").text("密 码 登 录")
        var obj = document.getElementById("line-set");
        obj.style.marginTop = "30%";
        $('title').html('登录')
    } else if (num === 1) {
        $("#useRegister").show();
        $("#useLogin").hide();
        $("#updatePwd").hide()
        $("#message").text("用 户 注 册")
        var obj = document.getElementById("line-set");
        obj.style.marginTop = "18%";
        $('title').html('注册')
    } else {
        $("#updatePwd").show();
        $("#useLogin").hide();
        $("#useRegister").hide();
        $("#message").text("密 码 找 回")
        var obj = document.getElementById("line-set");
        obj.style.marginTop = "18%";
        $('title').html('密码找回')
    }
}

$(function () {
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(login)', function (data) {
            console.log(data.field)
            //发送 ajax请求
            $.ajax({
                url: "/userInfo/loginAjax",
                type: "post",
                data: data.field,
                dataType: "json",//返回的数据类型
                success: function (data) {
                    if (data.info === "登录成功") {
                        window.location.href = "/index";
                        return false;
                    }
                    layer.alert(data.info);
                }

            })
            return false;
        });

        form.on('submit(sendEmail)', function (data) {
            console.log(data.field);
            //发送 ajax请求
            $.ajax({
                url: "/userInfo/sendEmail",
                type: "post",
                data: {"email": data.field.userEmail},
                dataType: "json",//返回的数据类型
                success: function (data) {
                    layer.alert(data.info);
                }
            })
            return false;
        });

        form.on('submit(register)', function (data) {
            console.log(data.field);
            //验证 邮箱验证码不能为空
            if (data.field.userPwd !== data.field.surePwd) {
                layer.alert("两次密码输入不一致");
                return false;
            }
            if (data.field.userCode === "") {
                layer.alert("邮箱验证码不能为空");
                return false;
            }
            //发送 ajax请求
            $.ajax({
                url: "/userInfo/registeredAjax",
                type: "post",
                data: data.field,
                dataType: "json",//返回的数据类型
                success: function (data) {
                    if (data.info === "注册成功") {
                        layer.alert("注册成功,5秒后跳转登录。");

                        function jump() {
                            window.location.href = '/userInfo/myLogin';
                        }

                        setTimeout(jump, 5000);
                        return false;
                    }
                    layer.alert(data.info);
                }
            })
            return false;
        });

        form.on('submit(resetPwd)', function (data) {
            console.log(data.field);
            //验证 邮箱验证码不能为空
            if (data.field.userPwd !== data.field.surePwd) {
                layer.alert("两次密码输入不一致");
                return false;
            }
            if (data.field.userCode == "") {
                layer.alert("邮箱验证码不能为空");
                return false;
            }
            //发送 ajax请求
            $.ajax({
                url: "/userInfo/resetPwd",
                type: "post",
                data: data.field,
                dataType: "json",//返回的数据类型
                success: function (data) {
                    if (data.info === "修改成功") {
                        layer.alert("修改成功,5秒后跳转登录。");

                        function jump() {
                            window.location.href = '/userInfo/myLogin';
                        }

                        setTimeout(jump, 5000);
                        return false;
                    }
                    layer.alert(data.info);
                }
            })
            return false;
        });
    });
})