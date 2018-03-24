var edit_vue=new Vue({
    el:'#form_edit',
    data:{
        account:{
            id:'',
            password:'',
            name:'',
            phone:'',
            createTime:'',
            updateTime:'',
        }
    },
    methods:{
        cancel:function () {
            window.location.href=_context_path+_servlet_path+'/dashboard/account/list';
        },
        load:function(id){
            var t=this;
            var url=_context_path+_servlet_path+'/dashboard/account/detail/'+id
            get(url,{},function (data) {
                t.account=data;
            },function (msg) {
                warn(msg.responseJSON.msg,msg.responseJSON.url);
            })
        }
    }
});
edit_vue.load(_to_load_id);
