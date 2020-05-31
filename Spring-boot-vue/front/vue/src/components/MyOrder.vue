<template>
  <el-container style="margin-top:100px">
    <el-aside width="210px">
      <div style="margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px">
        <el-button style="font-size:15px; width:190px" type="warning" @click="loginOut()" round>退出登录</el-button>
        <br /><br />用户id:{{uno}}<br/>用户名:{{uname}}<br />位置:我的订单<br /><br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="back()" round>返回主页</el-button>
        <br />
        <br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="createOrder()" round>上架商品</el-button>
        <br />
        <br />
        <el-button type="primary" style="font-size:15px; width:190px" @click="myCommodity()" round>我的商品</el-button>
      </div>
    </el-aside>

    <el-main>
      <!--商品表格-->
      <el-card>
        <el-row gutter="2">
          <el-col :span="3">
            <div>商品ID</div>
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

        <li v-for="(item,index) in orderForm" :key="item">
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

              <!--查看商品的详细信息-->
              <el-col :span="2" >
                <div>
                  <el-button type="primary" @click="indexData=index;drawer = true">详情</el-button>
                  <el-drawer
                    title="商品详情"
                    :visible.sync="drawer"
                    :direction="direction"
                    :before-close="handleClose"
                  >
                    <el-form :model="form">
                      <el-form-item label="序号： " :label-width="formLabelWidth">{{orderForm[indexData].cno}}</el-form-item>
                      <el-form-item label="商品名称: " :label-width="formLabelWidth">{{orderForm[indexData].cname}}</el-form-item>
                      <el-form-item label="价格/元: " :label-width="formLabelWidth">{{orderForm[indexData].cprice.toFixed(2)}}
                      </el-form-item>
                      <el-form-item label="描述: " :label-width="formLabelWidth">{{orderForm[indexData].cinformation}}</el-form-item>
                      <el-form-item label="售出状态: " :label-width="formLabelWidth">{{getstate(orderForm[indexData].cstate)}}
                      </el-form-item>
                      <el-form-item label="发售时间: " :label-width="formLabelWidth">{{orderForm[indexData].ctime}}</el-form-item>
                    </el-form>
                  </el-drawer>
                </div>
              </el-col>

              <el-col :span="2">
                <div>
                  <el-button type="danger" @click="indexData=index;preparerefund(orderForm[index].cstate)">退款</el-button>
                  <el-dialog
                    title="退款确认"
                    :visible.sync="dialogVisible"
                    width="30%"
                    :before-close="handleClose"
                  >
                    <el-button type="success" @click="fund(orderForm[indexData].cno,indexData)">确认退款</el-button>
                    <el-button type="warning" @click="dialogVisible = false">取消</el-button>
                  </el-dialog>
                </div>
              </el-col>
              <el-col :span="2">
                <div>
                  <el-button type="success" @click="indexData=index;prepareredeal(orderForm[index].cstate)">收货</el-button>
                  <el-dialog
                    title="收货确认"
                    :visible.sync="dialogVisible2"
                    width="30%"
                    :before-close="handleClose"
                  >
                    <el-button type="success" @click="deal(orderForm[indexData].cno,indexData)">确认收货</el-button>
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
      //发送请求获取商品信息
      orderForm: this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "post",
        url: "api/myBuyCommodity",
        data: this.$qs.stringify({ uno: this.$route.query.uno })
      })
        .then(res => {
          this.orderForm = res.data;
        })
        .catch(Error => {
          console.log(Error);
        })
    };
  },
  methods: {
    //退款
    fund(no,index) {
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "post",
        url: "api/fundCommodity",
        data: this.$qs.stringify({ cno: no })
      })
        .then(res => {
          console.log(res.data);
          this.dialogVisible=false;
          if (res.data == true) {
            this.$alert("退款成功", "提示", {
              confirmButtonText: "确定"
            })
            this.orderForm[index].cstate = 0;
          }
          else{
            this.$alert("退款失败", "提示", {
              confirmButtonText: "确定"
            });
          }
        })
        .catch(Error => {
          console.log(Error);
        });
    },

    //收货
    deal(no,index) {
      this.$axios({
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        method: "post",
        url: "api/dealCommodity",
        data: this.$qs.stringify({ cno: no })
      })
        .then(res => {
          console.log(res.data);
          this.dialogVisible2=false;
          if (res.data == true) {
            this.$alert("收货成功", "提示", {
              confirmButtonText: "确定"
            });
            this.orderForm[index].cstate = 2;
          }
          else{
            this.$alert("收货失败", "提示", {
              confirmButtonText: "确定"
            });
          }
        })
        .catch(Error => {
          console.log(Error);
        });
    },

    //根据state的值显示信息
    getstate(indexstate){
      if (indexstate === 1){
        return "已付款"
      }
      else if (indexstate === 2){
        return "已收货"
      }
      else{
        return "已退款"
      }
    },

    //退款前的检查
    preparerefund(fundstate){
      if(fundstate === 2 ){
        this.$alert("此商品已收货，无法退款", "提示", {
          confirmButtonText: "确定"
        });
      }
      else if(fundstate === 0){
        this.$alert("此商品已退款，无法再次退款", "提示", {
          confirmButtonText: "确定"
        });
      }
      else{
        this.dialogVisible = true
      }
    },

    //收货前的检查
    prepareredeal(dealstate){
      if(dealstate === 2 ){
        this.$alert("此商品已收货，无法再次收货", "提示", {
          confirmButtonText: "确定"
        });
      }
      else if(dealstate === 0){
        this.$alert("此商品已退款，无法收货", "提示", {
          confirmButtonText: "确定"
        });
      }
      else{
        this.dialogVisible2 = true
      }
    },

    //文字过多时以...替换
    showfull(text){
      if (text.length > 7){
        return text.substr(0, 5) + '...'
      }
      else{
        return text
      }
    },

    //退出登录
    loginOut() {
      this.uno = "";
      this.uname = "";
      this.$router.push("/");
    },

    //返回
    back() {
      this.$router.push({
        name: "Home",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    },

    //上架商品
    createOrder() {
      this.$router.push({
        name: "CreateOrder",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    },

    //查看出售的商品
    myCommodity() {
      this.$router.push({
        name: "MyCommodity",
        query: {
          uname: this.uname,
          uno: this.uno
        }
      });
    }
  }
};
</script>
