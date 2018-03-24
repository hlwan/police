<#include "../layout.ftl"/><@adminLayout>
<h2 class="page-header">编辑账户</h2>
<div class="form-horizontal" id="form_edit">
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="id"></label>
        <label  class="col-sm-1 control-label" for="id">账号</label>
        <div class="col-md-2">
            <input type="text" class="form-control disabled" id="id" placeholder="id" v-model="account.id" disabled>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="password"></label>
        <label  class="col-sm-1 control-label" for="password">密码</label>
        <div class="col-md-2">
            <input type="text" class="form-control" id="password" placeholder="密码" v-model="account.password" >
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="name"></label>
        <label  class="col-sm-1 control-label" for="name">姓名</label>
        <div class="col-md-2">
            <input type="text" class="form-control" id="name" placeholder="姓名" v-model="account.name">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="phone"></label>
        <label  class="col-sm-1 control-label" for="phone">手机</label>
        <div class="col-md-2">
            <input type="text" class="form-control" id="phone" placeholder="手机" v-model="account.phone">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="phone"></label>
        <label  class="col-sm-1 control-label" for="phone"></label>
        <div class="col-md-2">
            <button type="button" class="btn btn-primary" v-on:click="submit">提交</button>
            <button type="button" class="btn btn-default" v-on:click="cancel">取消</button>
        </div>
    </div>

</div>
<script>
    var _to_load_id='${id}';
</script>
<script src="${request.contextPath}/assets/js/account/edit.js?2018032401"></script>
</@adminLayout>