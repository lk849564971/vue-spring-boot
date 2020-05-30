<template>
  <el-container style="margin-top:100px">
    <el-aside width="210px">
      <div style="margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px">
        <el-button style="font-size:15px; width:190px" type="warning" @click="loginOut()" round>退出登录</el-button>
        <br /><br />用户id:{{uno}}<br/>用户名:{{uname}}<br />位置:我的商品<br /><br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="back()" round>返回</el-button>
        <br />
        <br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="myOrder()" round>我的订单</el-button>
      </div>
    </el-aside>

    <el-main>
      <el-card>
        <el-row gutter="2">
          <el-col :span="3">
            <div >商品ID</div>
          </el-col>
          <el-col :span="3">
            <div>名称</div>
          </el-col>
          <el-col :span="3">
            <div>价格</div>
          </el-col>
          <el-col :span="3">
            <div>描述</div>
          </el-col>
          <el-col :span="3">
            <div>购买状态</div>
          </el-col>
          <el-col :span="3">
            <div>发售时间</div>
          </el-col>
        </el-row>

        <li v-for="(item,index) in cForm" :key="item">
          <el-row gutter="2">
            <div>
              <el-col :span="3">
                <div>{{item.cno}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{showfull(item.cname)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{item.cprice.toFixed(2)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{showfull(item.cinformation)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{getstate(item.cstate)}}</div>
              </el-col>
              <el-col :span="3">
                <div>{{item.ctime}}</div>
              </el-col>

              <el-col :span="1.5" >
                <div>
                  <el-button type="primary" @click="indexData=index;drawer = true">详情</el-button>
                  <el-drawer
                    title="商品详情"
                    :visible.sync="drawer"
                    :direction="direction"
                    :before-close="handleClose"
                  >
                    <el-form :model="form">
                      <el-form-item label="序号： " :label-width="formLabelWidth">{{cForm[indexData].cno}}</el-form-item>
                      <el-form-item label="商品名称: " :label-width="formLabelWidth">{{showfull(cForm[indexData].cname)}}</el-form-item>
                      <el-form-item label="价格/元: " :label-width="formLabelWidth">{{cForm[indexData].cprice.toFixed(2)}}
                      </el-form-item>
                      <el-form-item label="描述: " :label-width="formLabelWidth">{{showfull(cForm[indexData].cinformation)}}</el-form-item>
                      <el-form-item label="售出状态: " :label-width="formLabelWidth">{{getstate(cForm[indexData].cstate)}}
                      </el-form-item>
                      <el-form-item label="发售时间: " :label-width="formLabelWidth">{{cForm[indexData].ctime}}</el-form-item>
                    </el-form>
                  </el-drawer>
                </div>
              </el-col>

              <el-col :span="2.5">
                <div>
                  <el-button type="danger" @click="indexData=index;prepareredelete(cForm[index].cstate)">删除记录</el-button>
                  <el-dialog
                    title="删除确认"
                    :visible.sync="dialogVisible"
                    width="30%"
                    :before-close="handleClose"
                  >
                    <el-button type="success" @click="out(cForm[indexData].cno,indexData)">确认删除</el-button>
                    <el-button type="warning" @click="dialogVisible = false">取消</el-button>
                  </el-dialog>
                </div>
              </el-col>

              <el-col :span="2">
                <div>
                  <el-button type="info" @click="indexData=index;preparereout(cForm[index].cstate)">下架商品</el-button>
                  <el-dialog
                    title="下架确认"
                    :visible.sync="dialogVisible2"
                    width="30%"
                    :before-close="handleClose"
                  >
                    <el-button type="success" @click="out(cForm[indexData].cno,indexData)">确认下架</el-button>
                    <el-button type="warning" @click="dialogVisible2 = false">取消</el-button>
                  </el-dialog>
                </div>
              </el-col>
            </div>
          </el-row>
        </li>
      </el-card>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      drawer: false,
      dialogVisible: false,
      dialogVisible2: false,
      uno: this.$route.query.uno,
      uname: this.$route.query.uname,
      indexData: 0,
      cForm: this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "post",
        url: "api/myCommodity",
        data: this.$qs.stringify({ uno: this.$route.query.uno })
      })
        .then(res => {
          this.cForm = res.data;
        })
        .catch(Error => {
          console.log(Error);
        })
    };
  },
  methods: {
    out(no,index) {
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "post",
        url: "api/deleteCommodity",
        data: this.$qs.stringify({ cno: no })
      })
        .then(res => {
          console.log(res.data);
          this.dialogVisible = false;
          this.dialogVisible2 = false;
          if (res.data == true) {
            this.$alert("操作成功", "提示", {
              confirmButtonText: "确定"
            });
            this.cForm[index].cstate = -1;
          }
          else{
            this.$alert("操作失败", "提示", {
              confirmButtonText: "确定"
            });
          }
        })
        .catch(Error => {
          console.log(Error);
        });
    },

    getstate(indexstate){
      if (indexstate === 1){
        return "买家已付款"
      }
      else if (indexstate === 2){
        return "买家已收货"
      }
      else if (indexstate === -1){
        return "已下架"
      }
      else{
        return "无人购买"
      }
    },
    prepareredelete(deletestate){
      if(deletestate === 0 ){
        this.$alert("此商品已发布，无法删除记录，只能下架", "提示", {
          confirmButtonText: "确定"
        });
      }
      else if(deletestate === 1){
        this.$alert("此商品已被锁定，无法删除记录，需等待交易完成之后才可删除记录", "提示", {
          confirmButtonText: "确定"
        });
      }
      else{
        this.dialogVisible = true
      }
    },

    preparereout(outstate){
      if(outstate === 2 ){
        this.$alert("此商品已交易完成，无法下架，只能删除记录", "提示", {
          confirmButtonText: "确定"
        });
      }
      else if(outstate === 1){
        this.$alert("此商品已被锁定，无法下架，需等待交易完成后才可删除记录", "提示", {
          confirmButtonText: "确定"
        });
      }
      else{
        this.dialogVisible2 = true
      }
    },

    showfull(text){
      if (text.length > 7){
        return text.substr(0, 5) + '...'
      }
      else{
        return text
      }
    },

    loginOut() {
      this.uno = "";
      this.uname = "";
      this.$router.push("/");
    },

    back() {
      this.$router.push({
        name: "Home",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    },

    //查看购买的商品
    myOrder() {
      this.$router.push({
        name: "MyOrder",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    }
  }
};
</script>
