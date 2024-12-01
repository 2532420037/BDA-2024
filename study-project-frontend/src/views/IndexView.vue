<template>
  <div class="main-container">
    <!-- 用户信息部分，右上角显示 -->
    <div class="user-info">
      <div class="welcome-text">
        欢迎您 <strong>{{ store.auth.user.username }}</strong>
      </div>
      <div>
        <span class="vip-status">{{ isVip ? 'VIP' : '普通用户' }}</span>
        <el-button @click="logout" type="danger" class="logout-button" plain>退出登录</el-button>
        <el-button
            v-if="!isVip"
            @click="upgradeVip"
            type="primary"
            class="upgrade-button"
            plain
        >升级为VIP</el-button>
      </div>
    </div>

    <!-- 论文搜索部分，居中显示 -->
    <div class="search-container">
      <h2 class="linked-papers-title">Linked Papers</h2>
      <el-input
          v-model="searchQuery"
          placeholder="请输入关键词进行搜索"
          class="search-input"
          @input="searchPapers"
      />
      <el-table
          :data="papers"
          style="width: 100%; margin-top: 20px"
          :show-header="false"
          stripe
          :pagination="pagination"
          class="papers-table"
      >
        <!-- 使用插槽来展示自定义内容 -->
        <el-table-column label="论文" prop="custom" :min-width="100">
          <template #default="{ row }">
            [{{ row.year }}] [{{ row.category }}] :
            <el-button @click="goToPaperDetail(row.id)" type="text">{{ row.title }}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          :current-page="pagination.currentPage"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          layout="prev, pager, next"
          @current-change="handlePageChange"
          class="pagination"
      />
    </div>
  </div>
</template>

<script setup>
import { get, post } from "@/net";
import { ElMessage } from "element-plus";
import { useStore } from "@/stores";
import { ref } from "vue";
import router from "@/router";

// 使用store获取用户信息
const store = useStore();
const searchQuery = ref(""); // 搜索关键词
const papers = ref([]); // 搜索结果
const pagination = ref({ currentPage: 1, pageSize: 10, total: 0 }); // 分页信息
const isVip = ref(store.auth.user.isVip); // 用户VIP状态

// 退出登录
const logout = () => {
  get('/api/auth/logout', (message) => {
    ElMessage.success(message);
    store.auth.user = null;
    router.push('/');
  });
}


const upgradeVip = () => {
  post('/api/auth/upgradeVip', { userId: store.auth.user.id }, (message) => {
    ElMessage.success(message); // 显示成功或失败信息
    if (message === 'VIP升级成功') {
      isVip.value = true; // 更新前端的VIP状态
      store.auth.user.isVip = 1; // 更新本地用户数据
    }
  });
}

// 搜索论文
const searchPapers = () => {
  get(`/api/paper/search?query=${searchQuery.value}&page=${pagination.value.currentPage}&pageSize=${pagination.value.pageSize}`, (data) => {
    papers.value = data.papers;
    pagination.value.total = data.totalCount;
  });
}

// 分页变化
const handlePageChange = (page) => {
  pagination.value.currentPage = page;
  searchPapers(); // 每次分页变化时重新搜索
}
// 前端跳转到论文详情页
const goToPaperDetail = (paperId) => {
  router.push({ name: 'PaperDetail', params: { id: paperId } });
}


// 初始化时调用搜索
searchPapers();

</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px 20px;
  background-color: #f5f5f5;
}

.user-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 1200px;
  margin-top: -40px;
  margin-bottom: 20px;
  padding: 10px 30px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.welcome-text {
  font-size: 18px;
  font-weight: bold;
}

.vip-status {
  font-size: 16px;
  color: #555;
  margin-right: 20px;
}

.logout-button,
.upgrade-button {
  margin-left: 10px;
}

.search-container {
  width: 100%;
  max-width: 1200px;
  text-align: center;
  background-color: #fff;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.linked-papers-title {
  font-size: 36px;
  font-weight: bold;
  color: #333;
  margin-top: auto;
  margin-bottom: 30px;
}

.search-input {
  width: 100%;
  max-width: 500px;
  margin-bottom: 20px;
  border-radius: 25px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.papers-table {
  width: 100%;
  margin-top: 20px;
}

.el-table {
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  overflow: hidden;
}

.el-table th {
  background-color: #f5f5f5;
  color: #333;
}

.pagination {
  margin-top: 20px;
  text-align: center;
  display: flex;
  justify-content: center;
}

.el-pagination button {
  border-radius: 50%;
  background-color: #f5f5f5;
}

.el-pagination .el-icon-arrow-left,
.el-pagination .el-icon-arrow-right {
  color: #333;
}
</style>
