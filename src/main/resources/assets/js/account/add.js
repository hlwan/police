new Vue({
    el:'#form_add',
    data:{
        name:'',
        id:'',
        password:'',
        phone:'',
    },
    methods:{
        submit:function () {
            var url=_context_path+_servlet_path+'/dashboard/account/add';
            post(url,JSON.stringify(this._data),function(){
                window.location.href=_context_path+_servlet_path+'/dashboard/account/list';
            },function(msg){
                warn(msg.responseJSON.msg,msg.responseJSON.url);
            });
        },
        cancel:function () {
            window.location.href=_context_path+_servlet_path+'/dashboard/account/list';
        }
    }
});