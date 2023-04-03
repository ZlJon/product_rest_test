import { ajax } from "/js/ajax.js";

/* 숨김 처리할 랩퍼*/
const $addWrapper = document.querySelector('.addWrapper');
const $inquiryWrapper = document.querySelector('.inquiryWrapper');
const $updateWrapper = document.querySelector('.updateWrapper');
const $listWrapper = document.querySelector('.listWrapper');
/* 각종 버튼들 */
const $setBtn = document.querySelector('.setBtn');
const $updateBtn = document.querySelector('.updateBtn');
const $deleteBtn = document.querySelector('.deleteBtn');
const $listBtn = document.querySelector('.listBtn');
const $addBtn = document.querySelector('.addBtn');
const $inquiryBtn = document.querySelector('.inquiryBtn');
/* 숨김 처리 */
$addBtn.addEventListener('click', ()=>{
  $listWrapper.classList.toggle('hidden');
  $addWrapper.classList.toggle('hidden');
});
$setBtn.addEventListener('click', ()=>{
  $listWrapper.classList.toggle('hidden');
  $addWrapper.classList.toggle('hidden');
});

/* ajax */
const $productFields = {pid, pname, quantity, price};

//등록 양식 클리어
const clearForm = () => {
  $productFields.pname.value = '';
  $productFields.quantity.value = '';
  $productFields.price.value = '';
};

//목록
const htmlRow = e =>
  `
  <div class="body">
  <div>${e.pid}</div>
  <div>${e.pname}</div>
  <div>${e.quantity}</div>
  <div>${e.price}</div>
  </div>
  `;

const list = res => {
  if (res.header.rtcd == '00') {
    const html = res.data.map(e=>htmlRow(e)).join('');
    document.querySelector('.table .body').innerHTML = html;
  } else {
    throw new Error(`${res.header.rtmsg}`);
  }
};

const list_h = e => {
  const url = `http://localhost:9080/api/products`;
  ajax.get(url).then(res=>res.json()).then(list).catch(console.error);
};
$listBtn.addEventListener('click', list_h);

//등록
const add = res => {
  if(res.header.rtcd == '00') {
    document.querySelector('form').reset();
    list_h();
  } else {
    throw new Error(`${res.header.rtmsg}`);
  }
};

const add_h = e => {
  const url = `http://localhost:9080/api/products`;
  const payload = {
    pname: $productFields.pname.value,
    quantity: $productFields.quantity.value,
    price: $productFields.price.value
  };

  ajax.post(url, payload).then(res=>res.json()).then(add).catch(console.error);
};
$setBtn.addEventListener('click', add_h);

//조회
const inquiry = res => {
  if(res.header.rtcd == '00') {
    $productFields.pid.value = res.data.pid;
    $productFields.pname.value = res.data.pname;
    $productFields.quantity.value = res.data.quantity;
    $productFields.price.value = res.data.price;
  } else if (res.header.rtcd == '99') {
    console.error;
    clearForm();
  } else {
    throw new Error(`${res.header.rtmsg}`);
  }
};
const inquiry_h = (e, pid) => {
  const url = `http://localhost:9080/api/products/${pid}`;
  ajax.get(url).then(res=>res.json()).then(inquiry).catch(console.error);
};
// $inquiryBtn.addEventListener('click', e=> {
//   const pid = $productFields.pid.value.trim();
//   inquiry_h(e, pid);
// });


//수정
const update = res => {
  if (res.header.rtcd == '00') {
    confirm(`상품번호: ${res.data.pid} 가 수정되었습니다.`);
    list_h();
  } else {
    throw new Error(`${res.header.rtmsg}`);
  }
};
const update_h = (e, pid) => {
  const url = `http://localhost:9080/api/products/${pid}`;
  const payload = {
    pname: $productFields.pname.value,
    quantity: $productFields.quantity.value,
    price: $productFields.price.value
  };
  ajax.patch(url, payload).then(res=>res.json()).then(update).catch(console.error);
};
$updateBtn.addEventListener('click', e=>{
  const pid = $productFields.pid.value.trim();
  update_h(e, pid);
});

//삭제
const del = res => {
  if(res.header.rtcd == '00') {
    confirm(`상품이 삭제되었습니다.`);
    document.querySelector('form').reset();
    list_h();
  } else {
    throw new Error(`${res.header.rtmsg}`);
  }
};
const del_h = (e, pid) => {
  const url = `http://localhost:9080/api/products/${pid}`
  ajax.delete(url).then(res=>res.json()).then(del).catch(console.error);
}
$deleteBtn.addEventListener('click', e=> {
  const pid = $productFields.pid.value.trim();
  if (pid) {
    confirm(`삭제 완료하였습니다.`);
    del_h(e, pid);
  }
});

list_h();
