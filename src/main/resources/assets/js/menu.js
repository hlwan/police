var menu_vue=new Vue({
    el: '#left_menu',
    data: {
        menus:[
            {
                caption: '账户管理',
                key:'account',
                url:_context_path+'/dashboard/account/list'
            },{
                caption: '统计信息',
                key:'visit',
                url:_context_path+'/dashboard/visit/list'
            }
        ],
        current:_current_menu
    },
    methods:{

    }
});
