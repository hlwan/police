var search_vue=new Vue({
    el:'#search_condition',
    data:{
        accountId:''
    },
    methods:{
        search:function(){
            page_vue.loadData();
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
                    "key":"accountId",
                },{
                    "caption":"访问时间",
                    "key":"createTime",
                },{
                    "caption":"地址",
                    "key":"url",
                },{
                    "caption":"结果",
                    "key":"status",
                },{
                    "caption":'操作',
                    'type':'render',
                    'renderer':function(data){
                        var h='<div class="btn-group" role="group">';
                        h=h+'<button type="button" class="btn btn-default" onclick="detail(\''+data.id+'\')">响应信息</button></div>';
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
                size=10;
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
            var url=_url_prefix+'/dashboard/visit/search?page='+page+'&size='+size;
            if(search_vue.accountId!=null&&search_vue.accountId!=''){
                if(search_vue.accountId.endsWith('@jsciq.gov.cn')){
                    url=url+'&accountId='+search_vue.accountId;
                }else{
                    url=url+'&accountId='+search_vue.accountId+'@jsciq.gov.cn';
                }
            }
            return url;
        }
    }
});

page_vue.loadData(0);

var response_detail_vue=new Vue({
    el:'#response_detail',
    data:{
        detail:''
    },
    methods:{
        search:function(){
            page_vue.loadData();
        }
    }
});

function detail(id){
    var datas=page_vue.page.page_data;
    for(var i=0;i<datas.length;i++){
        if(datas[i].id==id){
            response_detail_vue.detail=datas[i].response;
            $('#response_detail').modal('show');
            break;
        }
    }
}
