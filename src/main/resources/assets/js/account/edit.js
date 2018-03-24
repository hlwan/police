var edit_vue=new Vue({
    el:'#form_edit',
    data:{
        account:{
            id:'',
            name:'',
            password:'',
            phone:'',
        }
    },
    methods:{
        submit:function () {
            var url=_context_path+_servlet_path+'/dashboard/account/edit';
            post(url,JSON.stringify(this.account),function(){
                window.location.href=_context_path+_servlet_path+'/dashboard/account/list';
            },function(msg){
                warn(msg.responseJSON.msg,msg.responseJSON.url);
            });
        },
        cancel:function () {
            window.location.href=_context_path+_servlet_path+'/dashboard/account/list';
        },
        load:function(id){
            var t=this;
            var url=_context_path+_servlet_path+'/dashboard/account/detail/'+id
            get(url,{},function (data) {
                for(var p in t.account){
                    t.account[p]=data[p];
                }
            },function (msg) {
                warn(msg.responseJSON.msg,msg.responseJSON.url);
            })
        }
    }
});
edit_vue.load(_to_load_id);
