# 카카오페이 2019 클라이언트 인턴 채용 - 안드로이드 과제

고재두

## Preview

### 아이콘 & 스플래시 화면

<img src="./readme_resources/icon_and_splash.gif" width="30%" alt="icon and splash">

### 메인 화면(리스트 화면)
<div>
<img src="./readme_resources/search1.gif" width="30%" alt="search1" style="display: inline">
<img src="./readme_resources/search2.gif" width="30%" alt="search2" style="display: inline">
<img src="./readme_resources/search_error.gif" width="30%" alt="search_error" style="display: inline">
</div>

- 이전에 검색했던 단어를 자동완성으로 보여준다.
- 에러 발생 시 스낵바로 메세지가 나오고 재시도 버튼을 누르면 검색을 다시 시도한다.
- 검색 결과는 40개 이하일 경우 1줄에 2개, 초과일 경우 1줄에 3개의 아이템만 보이게 된다.

<img src="./readme_resources/scroll_and_page.gif" width="30%" alt="scroll_and_page">

- 리스트 헤더로 검색결과의 수를 보여준다.
- 리스트 풋터로 이전/다음 페이지로 이동할 수 있다.
- 리스트 스크롤 시 툴바가 사라진다.
- 이미지를 불러오는 동안 로딩 스피너가 나온다.

### 디테일 화면

<img src="./readme_resources/detail.gif" width="30%" alt="detail">

- 리스트에서 이미지를 클릭 시 디테일 화면으로 이동한다.
- 좌우 스와이프로 이전/다음 이미지로 이동할 수 있다.
- 화면 터치 시 이미지가 있는 웹페이지 이름이 나오고 이름을 클릭하면 웹페이지로 이동한다.


## Technologies Used
- Android
- Kotlin
- RxKotlin
- Clean Architecture
- MVVM
- Room
- Dagger
- Retrofit
- Gson
- Glide
- Junit
- Mockito


## UI
- [x] 노란색/갈색/흰색
- [x] 앱 아이콘

### 스플래시 화면
- [x] 0.7초 후 메인으로 이동
- [x] Image Searcher 
- [ ] 돋보기 + 그림

### 메인 화면
- [x] 툴바에 이름

#### 검색창
- [x] searchView : 검색어 입력 받기
- [x] 리스트 스크롤 아래로 이동하면 숨기기
- [x] 위로 이동하면 나오기
- [x] 텍스트 입력 중 자동완성 목록

#### 최근 본 이미지
- [ ] 처음엔 없고 검색어 상관없이 보면 리스트 최상단에 가로 스크롤로 추가됨(최대 10개)
- [ ] 숨기기 버튼

#### 리스트
- [x] 리사이클러뷰 그리드 3열
- [x] 썸네일 이미지
- [x] 이미지 불러올 때 로딩 스피너 보여주기 - progress
- [x] 헤더 풋터
- [x] 80개->검색결과 더 보기->페이지네이션 
- [x] 검색 결과가 40개 이하면 2열로 보여주기
- [x] 각 이미지 로딩 스피너, 에러 시 에러 이미지 보여주기 
- [x] 검색 네트워크 에러 발생 시 스낵바로 retry 버튼

 
### 디테일 화면
- [x] 이미지 전체 화면
- [x] 이미지 불러올 때 로딩 스피너 보여주기 - progress
- [x] 터치 하면 위 아래 반투명 검정배경으로 윗쪽 뒤로가기, 아랫쪽 이미지 정보
- [x] 원본 웹페이지 링크
- [x] 좌우 스크롤하면 이전/다음 이미지 - viewpager
- [ ] 확대
- [ ] 이미지 저장 기능
- [ ] 이전/다음 페이지 이미지 바로 연결하기
- [x] 에러 시 에러 이미지 보여주기 
