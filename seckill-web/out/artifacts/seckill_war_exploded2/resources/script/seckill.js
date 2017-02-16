/**
 * 交互逻辑
 */

//js的模块化写法
var seckill = {
    
    //秒杀相关ajax的url
    url:{
        now: function () {
            return '/seckill/time/now'
        },
        exposer: function (seckillId) {
            return '/seckill/'+seckillId+'/exposer'
        },
        execution: function (seckillId,md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },

    /**
     * 函数部分
     */
    //验证手机号函数
    validatePhone: function(phone){
        if(phone && phone.length==11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },
    //时间判断函数
    countdown: function(seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        if(nowTime > endTime){
            seckillBox.html('秒杀已结束!');
        }else if(nowTime < startTime){
            //seckillBox.html('秒杀还未开始...');
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function(event){//回调函数, 每次时间变化都会回调此函数
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function(){
                seckill.handleSeckillKill(seckillId,seckillBox);
            });
        }else{
            //seckillBox.html('秒杀开始!');
            seckill.handleSeckillKill(seckillId,seckillBox);
        }
    },
    //获取秒杀地址, 控制显示逻辑, 执行秒杀
    handleSeckillKill: function (seckillId,seckillBox) {
        seckillBox.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀!</button>');
        $.post(seckill.url.exposer(seckillId),{},function(result){//exposer
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    var md5 = exposer['md5'];
                    var killUrl = seckill.url.execution(seckillId,md5);
                    console.log('killUrl = '+killUrl);//TODO
                    $('#killBtn').one('click',function(){ //执行秒杀请求
                        $(this).addClass('disabled');
                        $.post(killUrl,{},function(result){//execution
                           if(result && result['success']){
                               var killResult = result['data'];
                               var state = killResult['state'];
                               var stateInfo = killResult['stateInfo'];
                               seckillBox.html('<span class="label label-success">'+stateInfo+'</span>');
                           }
                        });
                    });
                    seckillBox.show();
                }else{//可能因为客户端时间流动速度与服务端不同而导致实际上秒杀未开始甚至秒杀已结束(太早进入倒计时页面)
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log('result = '+result);
            }
        });
    },
    
    //详情页秒杀逻辑
    detail:{
        init:function (params) {
            //由于没有登录程序, 我们将用户信息存放在cookie中
            //用户信息(手机号)验证, 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show:true,//显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keyboard:false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone = ' + inputPhone);//TODO
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});//手机号写入cookie
                        window.location.reload();//刷新页面, 重新init
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }//已登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.url.now(),{},function(result) {//now
                if(result && result['success']){
                    var nowTime = result['data'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);//时间判断, 计时交互
                }else{
                    console.log('result = ' + result);
                }
            });
        }
    }


}