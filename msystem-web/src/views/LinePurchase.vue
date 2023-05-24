<template>
  <div class="manage">
    <div><span class="title">線材進貨</span></div>
    <div class="manage-header">
      <el-button type="primary" @click="addTable">+添加欄位</el-button>
    </div>
    <el-form :model="purchaseForm" ref="purchaseForm" :rules="purchaseForm.rules" inline-message>
      <div class="common-tabel">
        <!--    人員管理表格-->
        <el-table
            :data="purchaseForm.tableData"
            :row-class-name="rowClassName"
            height="90%"
            stripe
            row-key="viewId"
            style="width: 100%">
          <el-table-column label="序号" align="center" prop="id" width="50"></el-table-column>
          <el-table-column
              prop="pDate"
              label="進貨日"
              width="160">
            <template slot-scope="scope">
              <!-- prop的規則: 在普通的form表單中是一個對象,prop是對象的屬性. 表格是由多個對象組成的數組,在寫prop是需要根據索引給值.這里的tableData就相當於對象的屬性 !-->
              <!-- rules也要單獨給 -->
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.pDate'"
                  :rules='purchaseForm.rules.pDate'>
                <el-date-picker
                    v-model="purchaseForm.tableData[scope.row.id-1].pDate"
                    value-format="yyyy-MM-dd"
                    placeholder="選擇日期">
                </el-date-picker>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop= "companyName"
              label="進貨廠商"
              width="180">
            <template slot-scope="scope">
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.companyId'"
                  :rules='purchaseForm.rules.companyId'>
                <el-select v-model="purchaseForm.tableData[scope.row.id-1].companyId" placeholder="請選擇">
                  <el-option
                      v-for="item in companyData"
                      :key="item.companyId"
                      :label="item.companyName"
                      :value="item.companyId">
                  </el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop="lineId"
              label="線材編號"
              width="150">
            <template slot-scope="scope">
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.lineId'"
                  :rules='purchaseForm.rules.lineId'>
                <el-input v-model="purchaseForm.tableData[scope.row.id-1].lineId"
                          oninput="value=value.replace(/[^\d]/g,'')"
                          placeholder="請輸入線材編號">

                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop="lineType"
              label="端子"
              width="340">
            <template slot-scope="scope">
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.lineType'"
                  :rules='purchaseForm.rules.lineType'>
                <el-input v-model="purchaseForm.tableData[scope.row.id-1].lineType"
                          placeholder="請輸入端子">

                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop="lineName"
              label="品項"
              width="340">
            <template slot-scope="scope">
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.lineName'"
                  :rules='purchaseForm.rules.lineName'>
                <el-input v-model="purchaseForm.tableData[scope.row.id-1].lineName"
                          placeholder="請輸入品項">

                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop="totalCount"
              label="進貨數量"
              width="150">
            <template slot-scope="scope">
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.totalCount'"
                  :rules='purchaseForm.rules.totalCount'>
                <el-input v-model="purchaseForm.tableData[scope.row.id-1].totalCount"
                          oninput="value=value.replace(/[^\d]/g,'')"
                          placeholder="請輸入數量">

                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop="unitPrice"
              label="進貨單價"
              width="150">
            <template slot-scope="scope">
              <el-form-item
                  :prop="'tableData.' + scope.$index + '.unitPrice'"
                  :rules='purchaseForm.rules.unitPrice'>
                <el-input v-model="purchaseForm.tableData[scope.row.id-1].unitPrice"
                          oninput="value=value.trim().replace(/[^\d.]/g,'')"
                          placeholder="請輸入單價">

                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
              prop="addr"
              label="操作"
              width="120">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" @click="deleteTab(scope.row)">刪除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" :plain="true" class="submit_btn" @click="submit">提交</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import {mapActions, mapGetters, mapState} from "vuex";

export default {
  name:'LinePurchase',
  data(){
    return{
      purchaseForm:{
        tableData: [{
          pDate: '',
          companyName: '',
          companyId: '',
          lineId: '',
          lineType: '',
          lineName: '',
          totalCount: '',
          unitPrice: '',
        },],

        rules: {
          companyId: [
            { required: true, message: '請選擇進貨廠商', trigger: 'change' }
          ],
          lineId: [
            { required: true, message: '請輸入線材編號', trigger: 'blur' }
          ],
          lineType: [
            { required: true, message: '請輸入端子', trigger: 'blur' }
          ],
          lineName: [
            { required: true, message: '請輸入品項', trigger: 'blur' }
          ],
          totalCount: [
            { required: true, message: '請輸入進貨數量', trigger: 'blur' }
          ],
          unitPrice: [
            { required: true, message: '請輸入進貨單價', trigger: 'blur' }
          ],
        }
      },
    }
  },
  computed: {
    ...mapState('data',['goodData','companyData','employeeData','orderData','returnData','countData']),
  },
  methods: {
    ...mapActions('data', ['fetchGoodData', 'fetchCompanyData', 'fetchEmployeeData', 'fetchOrderData', 'fetchReturnData']),
    ...mapActions('line', ['fetchAddLineData']),
    //添加一行表單
    addTable () {
      let newRowData = {
        pDate: '',
        companyName: '',
        lineId: '',
        lineType: '',
        lineName: '',
        totalCount: '',
        unitPrice: '',
      }
      this.purchaseForm.tableData.push(newRowData);
    },
    // 提交數據
    submit () {
      this.$refs.purchaseForm.validate(valid => {
        //驗證通過，執行if內的代碼
        if(valid){
          // 新增新增線材資料
          this.fetchAddLineData(this.purchaseForm.tableData).then((resp) => {
            this.$message.success(resp.data);
          }).catch((error) => {
            this.$message.error(error.response.data);
          });

          //清空form表單的數據
          setTimeout(() => {
            //重置表單數據
            this.purchaseForm.tableData = [{
              pDate: '',
              companyName: '',
              lineId: '',
              lineType: '',
              lineName: '',
              totalCount: '',
              unitPrice: '',
            },];
            this.$refs.purchaseForm.resetFields();
          }, 2000);
        }
      })
    },
    //回調函數，定義表格的序號，可用來綁定資料數組
    rowClassName({row, rowIndex}) {
      row.id = rowIndex +1;
      },
    //刪除一行表格
    deleteTab (data) {
      //獲取當前行的數組位子
      let len = data.id - 1;
      //將此數組位子的資料刪除
      this.purchaseForm.tableData.splice(len,1);
    }
  },
  created() {
    this.fetchCompanyData();
  },
}
</script>

<style scoped>
.title{
  font-size: 24px;
  font-weight: bold;
  border-bottom: 1px solid black;
}
/* 日期选择框的宽度 */
.el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 140px;
}
.el-form-item {
  margin-bottom: auto;
}

.manage {
  height: 100%;
}

.manage .manage-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.manage .common-tabel {
  position: relative;
  height: 80vh;
}

.manage .common-tabel .pager {
  position: absolute;
  bottom: 40px;
  right: 20px;
}
.submit_btn{
  margin-left: 1400px;
  margin-top: 20px;
}
.manage .common-tabel .pager {
  position: absolute;
  bottom: 40px;
  right: 20px;
}

</style>