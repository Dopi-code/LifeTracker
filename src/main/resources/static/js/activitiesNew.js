
<script>

    document.addEventListener("DOMContentLoaded", function() {
    const startSelect = document.getElementById("acStart");
    const endSelect = document.getElementById("acEnd");

    // 00:00부터 23:50까지 10분 단위로 생성
    for (let h = 0; h < 24; h++) {
        for (let m = 0; m < 60; m += 10) {
            // 시와 분을 두 자리 문자열로 포맷팅 (예: 9 -> "09")
            const hour = String(h).padStart(2, '0');
            const minute = String(m).padStart(2, '0');
            const timeValue = `${hour}:${minute}`;

            // 시작 시간, 종료 시간 select 박스에 옵션 추가
            startSelect.add(new Option(timeValue, timeValue));
            endSelect.add(new Option(timeValue, timeValue));
        }
    }
    }); 
        // 24시간 라벨 생성
    const hourLabels = document.getElementById('hourLabels');
    for (let i = 0; i < 24; i++) {
        const span = document.createElement('span');
        span.innerText = String(i).padStart(2, '0') + '시';
        hourLabels.appendChild(span);
    console.log("24시간 라벨 생성(완료)");
    }


    // 144개 타임 슬롯 생성 (24시간 * 6칸)
    const timeGrid = document.getElementById('timeGrid');
    for (let i = 0; i < 144; i++) {
        const slot = document.createElement('div');
        slot.className = 'time-slot';
        slot.title = `${Math.floor(i/6)}시 ${(i%6)*10}분`;
        timeGrid.appendChild(slot);
    console.log("144개 타임 슬롯 생성(완료)");
    }

    // 시간 데이터를 인덱스화 하기(1010 -> 10시 10분 -> 60 + 1 = 61 인덱스)
    function convertTimeToIndex(timeData) {
    const hour = Math.floor(timeData / 100);
    const minute = timeData % 100;
    return (hour * 6) + minute/10;
    console.log("시간 데이터를 인덱스화 하기(완료)");
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
    console.log("시간표 셀의 색깔을 바꾸기(완료)");
    }
    // 기능 실행 1(샘플 데이터)
    colorActivity("1200", "1330", "#ffa07a");

    // 기능 실행 2(DB 데이터)
    {{#DailyTrackReadFormList}}
        console.log("일단 머스테이치 삽입 되는지 확인");
        colorActivity({{acStart}}, {{acEnd}}, "{{acColor}}");
        console.log("acStart :",{{acStart}});
        console.log("acEnd :",{{acEnd}});
    {{/DailyTrackReadFormList}}
</script>