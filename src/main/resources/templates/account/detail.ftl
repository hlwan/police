<#include "../layout.ftl"/><@adminLayout>
<h2 class="page-header">账号详情</h2>
<div class="form-horizontal" id="form_edit">
    <div class="form-group">
        <label  class="col-sm-1 control-label"></label>
        <label  class="col-sm-1 control-label">账号</label>
        <div class="col-md-8">
            <p class="form-control-static">{{account.id}}</p>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="password"></label>
        <label  class="col-sm-1 control-label" for="password">密码</label>
        <div class="col-md-8">
            <p class="form-control-static">{{account.password}}</p>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="name"></label>
        <label  class="col-sm-1 control-label" for="name">姓名</label>
        <div class="col-md-8">
            <p class="form-control-static">{{account.name}}</p>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="phone"></label>
        <label  class="col-sm-1 control-label" for="phone">手机</label>
        <div class="col-md-8">
            <p class="form-control-static">{{account.phone}}</p>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="createTime"></label>
        <label  class="col-sm-1 control-label" for="createTime">创建时间</label>
        <div class="col-md-8">
            <p class="form-control-static">{{account.createTime}}</p>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="updateTime"></label>
        <label  class="col-sm-1 control-label" for="updateTime">更新时间</label>
        <div class="col-md-8">
            <p class="form-control-static">{{account.updateTime}}</p>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-1 control-label" for="phone"></label>
        <label  class="col-sm-1 control-label" for="phone"></label>
        <div class="col-md-2">
            <button type="button" class="btn btn-primary" v-on:click="cancel">返回</button>
        </div>
    </div>
</div>
    <script>
    var _to_load_id='${id}';
</script>
<script src="${request.contextPath}/assets/js/account/detail.js?2018032401"></script>
</@adminLayout>