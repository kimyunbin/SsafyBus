<template>
  <div>
    <div class="head">
      <button>질문하기</button>
      <p>X icon</p>
    </div>
    <div class="board">
      <table class="board-head">
        <colgroup>
					<col width="3%" />
					<col width="70%" />
					<col width="7%" />
					<col width="7%" />
				</colgroup>
        <tr>
          <th>no</th>
          <th>제목</th>
          <th>아이디</th>
          <th>작성일</th>
        </tr>
				<tr>
          <td>3</td>
          <td>이거 어캐해요</td>
          <td>임팡훈</td>
          <td>3일전</td>
				</tr>
      </table>
      <br>
      <div class="board-content">

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Help",
}
</script>

<style lang="scss" scoped>

.board {
  border: 4px solid yellowgreen;
  border-radius: 10px;
  max-width: 1200px;
  background-color: white;
  padding-left: 30px;
  padding-bottom: 30px;
  margin-top: 50px;
  margin-left: auto;
  margin-right: auto;
}
.board-head th{border-top:1px solid #888;}
.board-head th, .tbList td{
  border-bottom:1px solid #eee; padding:5px 0;
}
.board-head td.txt_left{text-align:left;}
</style>