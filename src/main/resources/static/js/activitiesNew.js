    // 아래 코드는 HTML의 <script>에서 JS 하이라이팅이 되지 않아 임시로 적는 곳이다.
    // 24시간 라벨 생성
    const hourLabels = document.getElementById('hourLabels');
    for (let i = 0; i < 24; i++) {
        const span = document.createElement('span');
        span.innerText = String(i).padStart(2, '0') + '시';
        hourLabels.appendChild(span);
    }

    // 144개 타임 슬롯 생성 (24시간 * 6칸)
    const timeGrid = document.getElementById('timeGrid');
    for (let i = 0; i < 144; i++) {
        const slot = document.createElement('div');
        slot.className = 'time-slot';
        slot.title = `${Math.floor(i/6)}시 ${(i%6)*10}분`;
        timeGrid.appendChild(slot);
    }

    // 시간 데이터를 인덱스화 하기(1010 -> 10시 10분 -> 60 + 1 = 61 인덱스)
    function convertTimeToIndex(timeData) {
    const hour = Math.floor(timeData / 100);
    const minute = timeData % 100;
    return (hour * 6) + minute/10;
    }

    // 해당 인덱스의 시간표 셀의 색깔을 바꾸는 기능
    function colorActivity(acStart, acEnd, acColor) {
        const startIndex = convertTimeToIndex(acStart);
        const endIndex = convertTimeToIndex(acEnd);
        const slots = document.querySelectorAll('.time-slot'); // 144개의 셀들

        for (let i=startIndex; i<endIndex; i++){
            if (slots[i]) {
                slots[i].style.backgroundColor = acColor;
            }
        }
    }

    // 기능 실행
    colorActivity({{acStart}}, {{acEnd}}, {{acColor}});
    console.log("acStart :",acStart);
    console.log("acEnd :",acEnd);
    colorActivityRange("12:00", "13:30", "#ffa07a");
