<template>
  <div class="paper-detail-container">
    <div class="paper-header">
      <h2 class="paper-title">{{ paper.title }}</h2>
      <p class="paper-abstract"><strong>摘要:</strong> {{ paper.abstracts }}</p>
    </div>

    <div class="section">
      <h3 class="section-title">引用论文:</h3>
      <el-table :data="citedPapers" class="paper-table" border>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="作者" prop="authors"></el-table-column>
      </el-table>
    </div>

    <!-- VIP 用户额外显示的内容 -->
    <div v-if="isVip" class="section">
      <h3 class="section-title">相似论文:</h3>
      <el-table :data="similarPapers" class="paper-table" border>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="作者" prop="authors"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { get } from "@/net";  // 假设你有一个 `get` 函数来发送请求
import { useStore } from "@/stores";
import { ElMessage } from 'element-plus';

const store = useStore();
const route = useRoute();
const paper = ref({});
const citedPapers = ref([]);
const similarPapers = ref([]);
const isVip = ref(store.auth.user.isVip);  // 判断用户是否为VIP

// 获取论文详情
const getPaperDetail = () => {
  const paperId = route.params.id;
  get(`/api/paper/${paperId}`, (data) => {
    console.log(data)
    paper.value = data;
    // 引用的论文
    if (isVip.value) {
      similarPapers.value = data.similarPapers;  // VIP用户看到的相似论文
    }
  }, () => {
    ElMessage.error("获取论文详情失败！");
  });
}

// 页面加载时调用
onMounted(() => {
  getPaperDetail();
});
</script>

<style scoped>
/* 页面容器 */
.paper-detail-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 30px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
}

/* 论文标题与摘要 */
.paper-header {
  margin-bottom: 40px;
  text-align: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
}

.paper-title {
  font-size: 36px;
  font-weight: 700;
  color: #333;
  margin-bottom: 15px;
  text-transform: uppercase;
  letter-spacing: 2px;
}

.paper-abstract {
  font-size: 18px;
  color: #555;
  line-height: 1.6;
  text-align: center;
}

/* 各部分样式 */
.section {
  margin-bottom: 40px;
}

.section-title {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #2d87f0;
  text-transform: uppercase;
  letter-spacing: 1px;
}

/* 表格样式 */
.paper-table {
  width: 100%;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: all 0.3s ease-in-out;
}

.paper-table .el-table-column {
  padding: 10px;
}

.paper-table .el-table-column .cell {
  font-size: 16px;
}

.paper-table .el-table-column label {
  font-weight: 600;
}

.el-table th {
  background-color: #f2f2f2;
  color: #555;
}

.el-table td {
  color: #333;
}

.el-table th, .el-table td {
  text-align: center;
}

.el-table-pagination {
  margin-top: 20px;
  text-align: center;
  color: #555;
}

/* 悬停效果 */
.paper-table .el-table-column .cell:hover {
  background-color: #f0f9ff;
  cursor: pointer;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .paper-detail-container {
    padding: 20px;
  }

  .paper-title {
    font-size: 28px;
  }

  .paper-abstract {
    font-size: 16px;
  }

  .section-title {
    font-size: 22px;
  }

  .paper-table .el-table-column .cell {
    font-size: 14px;
  }
}
</style>
