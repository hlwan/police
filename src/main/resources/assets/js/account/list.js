var search_vue=new Vue({
    el:'#search_condition',
    data:{
        account:'',
        phone:'',
        name:''
    },
    methods:{
        search:function(){
            page_vue.loadData();
        },
        add:function(){
            window.location.href=_context_path+_servlet_path+'/dashboard/account/addPage';
        }
    }
});

var page_vue=new Vue({
    el: '#data-table',
    data: {
        page:{
            page_headers:[
                {
                    "caption":"账号",
                    "key":"id",
                },{
                    "caption":"姓名",
                    "key":"name",
                },{
                    "caption":"电话",
                    "key":"phone",
                },{
                    "caption":"创建时间",
                    "key":"createTime",
                },{
                    "caption":"更新时间",
                    "key":"updateTime",
                },{
                    "caption":'操作',
                    'type':'render',
                    'renderer':function(data){
                        var h='<div class="btn-group" role="group">';
                        h=h+'<button type="button" class="btn btn-default" onclick="edit(\''+data.id+'\')">编辑</button>' +
                            '<button type="button" class="btn btn-default" onclick="detail(\''+data.id+'\')">查看</button>' +
                            '<button type="button" class="btn btn-default" onclick="showDelete(\''+data.id+'\')">删除</button>'
                            '</div>';
                        return h;
                    }
                }
            ],
            page_data:[],
            page_info:{
                number:0,
                totalPages:0,
                totalElements:0
            },
            dataAtPage:function (page) {
                page_vue.loadData(page);
            }
        }
    },
    methods:{
        loadData:function(page,size){
            if(!page||page==null){
                page=0;
            }
            if(!size||size==null){
                size=20;
            }
            var t=this;
            get(this.getUrl(page,size),{},function (data) {
                t.page.page_data=data.content;
                for( var o in t.page.page_info){
                    t.page.page_info[o]=data[o];
                }
            },function (msg) {
                warn(msg.responseJSON.msg,msg.responseJSON.url);
            });
        },
        getUrl:function (page,size) {
            var url=_url_prefix+'/dashboard/account/search?page='+page+'&size='+size;
            if(search_vue.account!=null&&search_vue.account!=''){
                if(search_vue.account.endsWith('@jsciq.gov.cn')){
                    url=url+'&id='+search_vue.account;
                }else{
                    url=url+'&id='+search_vue.account+'@jsciq.gov.cn';
                }

            }
            if(search_vue.name!=null&&search_vue.name!=''){
                url=url+'&accountName='+search_vue.name;
            }
            if(search_vue.phone!=null&&search_vue.phone!=''){
                url=url+'&phone='+search_vue.phone;
            }
            return url;
        }
    }
});

page_vue.loadData(0);

var confirm_vue=new Vue({
    el:"#del_confirm",
    data:{
        account:'',
    },
    methods:{
        submit:function () {
            del(this.account);
        }
    }
});

function showDelete(id){
    confirm_vue.account=id;
    $('#del_confirm').modal('show');
}

function detail(id){
    window.open(_url_prefix+'/dashboard/account/detailPage/'+id);
}

function edit(id){
    window.open(_url_prefix+'/dashboard/account/editPage/'+id);
}

function del(id){
    var url=_url_prefix+'/dashboard/account/delete/'+id;
    post(url,{},function(){
        $('#del_confirm').modal('hide');
        setTimeout(page_vue.loadData(page_vue.page.page_info.number),1000);
    },function(msg){
        warn(msg.responseJSON.msg);
    });
}