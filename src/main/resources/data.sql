INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (1, '암살자', '암살할 캐릭터의 이름을 선언합니다. 암살된 캐릭터는 자기 차례를 쉽니다');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (2, '도둑', '금화를 훔쳐올 목표 캐릭터의 이름을 선언합니다. 목표 캐릭터가 호명될 때, 목표 캐릭터의 개인 금고에서 금화 전부를 즉시 가져옵니다');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (3, '마술사', '다른 플레이어와 손에 든 카드 전부를 서로 교환하건, 또는 손에 든 카드를 원하는 만큼 버리고 똑같은 수만큼 건물 카드 더미에서 카드를 가져옵니다.');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (4, '왕', '왕관을 가져옵니다. 자기 도시의 귀족 건물 수만큼 금화를 받습니다.');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (5, '주교', '자기 도시의 건물에는 8번째 캐릭터가 절대로 능력을 쓸 수 없습니다. 자기 도시의 종교 건물 수만큼 금화를 받습니다.');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (6, '상인', '추가로 금화 1닢을 받습니다. 자기 도시의 상업 건물 수만큼 금화를 받습니다.');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (7, '건축가', '추가로 카드 2장을 받습니다. 건물을 3채까지 건설할 수 있습니다.');
INSERT INTO JOB (NO, NAME, DESCRIPTION) VALUES (8, '장군', '원하는 건물 한 채를 파괴할 수 있습니다(파괴비용은 해당 건물 건설비용보다 금화 1닢이 적습니다). 자기 도시의 군사 건물 수만큼 금화를 받습니다.');

INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('망루', 1, 'A', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('사원', 1, 'R', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('술집', 1, 'C', 5);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('교역소', 2, 'C', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('시장', 2, 'C', 4);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('예배당', 2, 'R', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('감옥', 2, 'A', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('병영', 3, 'A', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('부두', 3, 'C', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('수도원', 3, 'R', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('저택', 3, 'N', 5);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('항구', 4, 'C', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('성', 4, 'N', 4);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('궁전', 5, 'N', 3);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('대성당', 5, 'R', 2);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('요새', 5, 'A', 2);
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT) VALUES ('시청', 5, 'C', 2);

INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('유령지구', 2, 'S', 1, '게임이 종료되면 유령 지구를 원한는 종류의 건물로 간주합니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('외성', 3, 'S', 1, '절대로 8번 캐릭터 능력의 목표가 되지 않습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('천문대', 4, 'S', 1, '자원 얻기 행동으로 건물 카드를 얻는다면, (2장이 아니라) 3장을 가져가서 살펴봅니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('대장간', 5, 'S', 1, '차례마다 한 번, 금화 2닢을 내고 카드 3장을 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('실험실', 5, 'S', 1, '차례마다 한 번, 손에 든 카드 1장을 버리고 금화 2닢을 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('도서관', 5, 'S', 1, '자원 얻기 행동으로 건물 카드를 얻는다면, 가져간 카드를 전부 손에 듭니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('드래곤 게이트', 6, 'S', 1, '게임이 종료되면 추가로 2점을 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('마법학교', 6, 'S', 1, '자신의 도시 건물 종류에 따라 자원을 받는 능력을 사용할 때, 마법학교를 원하는 종류의 건물로 간주합니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('장성', 6, 'S', 1, '8번 캐릭터는 장성이 있는 도시 건물(장성 제외)에 능력을 사용할 때 금화 1닢을 더 내야 합니다');

/*
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('병기고', 3, 'S', 1, '자기 차례에, 병기고를 파괴하고 다른 건물 1채를 파괴할 수 있습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('박물관', 4, 'S', 1, '차례마다 한 번, 손에 든 건물 카드 1장을 박물관 아래 넣습니다. 게임이 종료되면, 박물관 아래 있는 카드 1장당 1점을 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('구빈원', 4, 'S', 1, '자기 차례를 끝냈을 때 자기 개인 금고에 금화가 없다면, 금화 1닢을 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('구빈원', 5, 'S', 1, '게임이 종료되면 개인 금고에 남아있는 금화 1닢당 1점씩을 추가로 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('소원의 우물', 5, 'S', 1, '게임이 종료되면 자기 도시에 건설된 특수 건물 1채당(소원의 우물 포함) 추가로 1점씩을 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('지도 보관실', 5, 'S', 1, '게임이 종료되면 손에 든 건물 카드 1장당 1점씩을 추가로 받습니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('공장', 5, 'S', 1, '특수 건물을 건설할 때 금화 1닢을 적게 냅니다');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('공원', 6, 'S', 1, '자기 차례를 끝냈을 때 손에 든 건물 카드가 없다면, 카드 2장을 받습니다');


INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('비밀 금고', 0, 'S', 1, '비밀 금고는 절대로 도시에 건설할 수 없습니다. 게임이 종료되었을 때, 손에 든 비밀 금고를 공개하고 추가로 3점을 받습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('마구간', 2, 'S', 1, '마구간 건설은 이번 차례의 건설 횟수에 포함되지 않습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('골조', 3, 'S', 1, '골조를 파괴하면 건설비용 지불 없이 건물 1채를 건설할 수 있습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('동상', 3, 'S', 1, '게임이 종료되었을 때 왕관을 가지고 있다면 추가로 5점을 받습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('바실리카', 4, 'S', 1, '게임이 종료되었을 때 자기 도시의 건물 중 건설비용이 홀수인 건물당 추가로 1점씩을 받습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('기념물', 4, 'S', 1, '자기 도시에 건물이 5채 이상 건설되어 있다면 기념물을 건설할 수 없습니다. 기념물은 도시 완성을 판별할 때 건물 2채로 간주합니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('의사당', 5, 'S', 1, '게임이 종료되었을 때 자기 도시에 같은 종류의 건물이 최소 3채 건설되어 있다면 추가로 3점을 받습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('공도묘지', 5, 'S', 1, '자기 도시의 아무 건물 1채를 파괴하면 건설비용 지불 없이 공동묘지를 건설할 수 있습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('도적 소굴', 6, 'S', 1, '도적 소굴을 건설할 때 건설비용의 이룹 또는 전부를 (금화가 아니라) 건물 카드로 낼 수 있습니다. (금화 1닢당 카드 1장)');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('금광', 6, 'S', 1, '자원 얻기 행동으로 금화를 얻는다면, 금화 1닢을 추가로 얻습니다.');
INSERT INTO BUILDING(NAME, COST, TYPE, COUNT, DESCRIPTION) VALUES ('극장', 6, 'S', 1, '선택 단계가 종료될 때, 다른 플레이어와 캐릭터 카드를 바꿀 수 있습니다');
*/