INSERT INTO customers (username, email, password, name, role, created_at, updated_at, deleted_at) VALUES
                                                                                                      ('test', 'test@test.com', '$2a$04$5rLEgDtcvmbDu0qS2FT8I.C5ZFZjNeANS.vo9hiQAcAmS93tkU25q', '발표용 계정', 10, NOW(), NOW(), NULL),
                                                                                                      ('user2', 'user2@example.com', 'password123', 'Jane Smith', 20, NOW(), NOW(), NULL),
                                                                                                      ('user3', 'user3@example.com', 'password123', 'Michael Johnson', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin1', 'admin1@example.com', 'adminpass123', 'Admin One', 10, NOW(), NOW(), NULL),
                                                                                                      ('admin2', 'admin2@example.com', 'adminpass123', 'Admin Two', 10, NOW(), NOW(), NULL),
                                                                                                      ('user4', 'user4@example.com', 'password123', 'Emily Davis', 20, NOW(), NOW(), NULL),
                                                                                                      ('user5', 'user5@example.com', 'password123', 'David Lee', 20, NOW(), NOW(), NULL),
                                                                                                      ('user6', 'user6@example.com', 'password123', 'Sarah Wilson', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin3', 'admin3@example.com', 'adminpass123', 'Admin Three', 10, NOW(), NOW(), NULL),
                                                                                                      ('user7', 'user7@example.com', 'password123', 'Daniel Brown', 20, NOW(), NOW(), NULL),
                                                                                                      ('user8', 'user8@example.com', 'password123', 'Olivia Martinez', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin4', 'admin4@example.com', 'adminpass123', 'Admin Four', 10, NOW(), NOW(), NULL),
                                                                                                      ('user9', 'user9@example.com', 'password123', 'Sophia Taylor', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin5', 'admin5@example.com', 'adminpass123', 'Admin Five', 10, NOW(), NOW(), NULL),
                                                                                                      ('user10', 'user10@example.com', 'password123', 'Lucas Anderson', 20, NOW(), NOW(), NULL),
                                                                                                      ('user11', 'user11@example.com', 'password123', 'Ava Thomas', 20, NOW(), NOW(), NULL),
                                                                                                      ('user12', 'user12@example.com', 'password123', 'Liam Jackson', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin6', 'admin6@example.com', 'adminpass123', 'Admin Six', 10, NOW(), NOW(), NULL),
                                                                                                      ('user13', 'user13@example.com', 'password123', 'Mason Harris', 20, NOW(), NOW(), NULL),
                                                                                                      ('user14', 'user14@example.com', 'password123', 'Isabella Clark', 20, NOW(), NOW(), NULL),
                                                                                                      ('user15', 'user15@example.com', 'password123', 'Ethan Lewis', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin7', 'admin7@example.com', 'adminpass123', 'Admin Seven', 10, NOW(), NOW(), NULL),
                                                                                                      ('user16', 'user16@example.com', 'password123', 'Charlotte Young', 20, NOW(), NOW(), NULL),
                                                                                                      ('user17', 'user17@example.com', 'password123', 'Amelia King', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin8', 'admin8@example.com', 'adminpass123', 'Admin Eight', 10, NOW(), NOW(), NULL),
                                                                                                      ('user18', 'user18@example.com', 'password123', 'Harper Scott', 20, NOW(), NOW(), NULL),
                                                                                                      ('user19', 'user19@example.com', 'password123', 'Ella Green', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin9', 'admin9@example.com', 'adminpass123', 'Admin Nine', 10, NOW(), NOW(), NULL),
                                                                                                      ('user20', 'user20@example.com', 'password123', 'Mila Adams', 20, NOW(), NOW(), NULL),
                                                                                                      ('user21', 'user21@example.com', 'password123', 'Jackson Nelson', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin10', 'admin10@example.com', 'adminpass123', 'Admin Ten', 10, NOW(), NOW(), NULL),
                                                                                                      ('user22', 'user22@example.com', 'password123', 'Benjamin Hall', 20, NOW(), NOW(), NULL),
                                                                                                      ('user23', 'user23@example.com', 'password123', 'Amos Wright', 20, NOW(), NOW(), NULL),
                                                                                                      ('admin11', 'admin11@example.com', 'adminpass123', 'Admin Eleven', 10, NOW(), NOW(), NULL);

INSERT INTO tasks (
    assignee_id,
    title,
    description,
    priority,
    status,
    due_date,
    created_at,
    updated_at,
    deleted_at
) VALUES
      (
          1,
          'Task 1 - 댓글 기능 구현',
          '댓글 및 대댓글 기능을 구현합니다.',
          10,
          10,
          DATE_ADD(NOW(), INTERVAL 7 DAY),
          NOW(),
          NOW(),
          NULL
      ),
      (
          1,
          'Task 2 - 댓글 조회 최적화',
          '댓글 조회 성능 개선 및 정렬 로직을 구현합니다.',
          20,
          20,
          DATE_ADD(NOW(), INTERVAL 10 DAY),
          NOW(),
          NOW(),
          NULL
      ),
      (
          1,
          'Task 3 - 댓글 삭제 처리',
          '댓글 soft delete 처리 및 조회 예외를 구현합니다.',
          30,
          30,
          DATE_ADD(NOW(), INTERVAL 14 DAY),
          NOW(),
          NOW(),
          NULL
      );

INSERT INTO teams (name, description, created_at, updated_at, deleted_at) VALUES
                                                                              ('Backend Team', '백엔드 API 및 서버 로직을 담당하는 팀', NOW(), NOW(), NULL),
                                                                              ('Frontend Team', '프론트엔드 UI 및 UX 개발을 담당하는 팀', NOW(), NOW(), NULL),
                                                                              ('DevOps Team', '배포, 인프라, CI/CD를 관리하는 팀', NOW(), NOW(), NULL),
                                                                              ('QA Team', '테스트 및 품질 관리를 담당하는 팀', NOW(), NOW(), NULL),
                                                                              ('Planning Team', '기획 및 요구사항 정의를 담당하는 팀', NOW(), NOW(), NULL);

INSERT INTO comments (customer_id, task_id, parent_id, content, created_at, updated_at, deleted_at) VALUES
-- ===== 기본 댓글 (1 ~ 15) =====
(1, 1, NULL, 'Task1 첫 번째 댓글입니다.', NOW(), NOW(), NULL),   -- id 1
(2, 1, NULL, '작업 진행 상황 공유 부탁드립니다.', NOW(), NOW(), NULL), -- id 2
(3, 1, NULL, '확인했습니다.', NOW(), NOW(), NULL), -- id 3
(4, 1, NULL, '이 부분은 잘 구현된 것 같습니다.', NOW(), NOW(), NULL), -- id 4
(5, 1, NULL, '수정이 필요해 보입니다.', NOW(), NOW(), NULL), -- id 5

(1, 1, NULL, '테스트는 완료되었나요?', NOW(), NOW(), NULL), -- id 6
(2, 1, NULL, '배포 일정이 궁금합니다.', NOW(), NOW(), NULL), -- id 7
(3, 1, NULL, '관련 문서가 있으면 공유해주세요.', NOW(), NOW(), NULL), -- id 8
(4, 1, NULL, '이슈는 현재 없는 상태입니다.', NOW(), NOW(), NULL), -- id 9
(5, 1, NULL, '성능 개선 여지가 있어 보입니다.', NOW(), NOW(), NULL), -- id 10

(1, 1, NULL, '리팩토링 계획이 있나요?', NOW(), NOW(), NULL), -- id 11
(2, 1, NULL, 'API 응답 시간이 조금 느립니다.', NOW(), NOW(), NULL), -- id 12
(3, 1, NULL, '코드 리뷰 요청드립니다.', NOW(), NOW(), NULL), -- id 13
(4, 1, NULL, '전반적으로 잘 작성된 코드입니다.', NOW(), NOW(), NULL), -- id 14
(5, 1, NULL, '추가 기능 논의가 필요합니다.', NOW(), NOW(), NULL), -- id 15

-- ===== 대댓글 (16 ~ 30) =====
(2, 1, 1, '첫 번째 댓글에 대한 답변입니다.', NOW(), NOW(), NULL),
(3, 1, 1, '저도 같은 의견입니다.', NOW(), NOW(), NULL),

(1, 1, 2, '오늘 중으로 공유하겠습니다.', NOW(), NOW(), NULL),
(4, 1, 2, '확인 감사합니다.', NOW(), NOW(), NULL),

(5, 1, 5, '어떤 부분을 수정하면 좋을까요?', NOW(), NOW(), NULL),

(2, 1, 6, '테스트는 거의 완료되었습니다.', NOW(), NOW(), NULL),

(3, 1, 7, '이번 주 금요일 배포 예정입니다.', NOW(), NOW(), NULL),

(1, 1, 8, '문서 정리해서 공유드릴게요.', NOW(), NOW(), NULL),

(5, 1, 10, '캐싱 적용을 고려해보면 좋을 것 같습니다.', NOW(), NOW(), NULL),

(4, 1, 11, '리팩토링 일정은 다음 스프린트입니다.', NOW(), NOW(), NULL),

(1, 1, 12, '해당 부분 최적화 진행 중입니다.', NOW(), NOW(), NULL),

(2, 1, 13, '리뷰 완료 후 코멘트 남기겠습니다.', NOW(), NOW(), NULL),

(3, 1, 15, '다음 회의 때 논의해보죠.', NOW(), NOW(), NULL);

INSERT INTO team_members (
    team_id,
    customer_id,
    created_at,
    updated_at,
    deleted_at
) VALUES
-- ===== Team 1 =====
(1, 1, NOW(), NOW(), NULL),
(1, 2, NOW(), NOW(), NULL),
(1, 3, NOW(), NOW(), NULL),
(1, 4, NOW(), NOW(), NULL),

-- ===== Team 2 =====
(2, 5, NOW(), NOW(), NULL),
(2, 6, NOW(), NOW(), NULL),
(2, 7, NOW(), NOW(), NULL),
(2, 8, NOW(), NOW(), NULL),

-- ===== Team 3 =====
(3, 9, NOW(), NOW(), NULL),
(3, 10, NOW(), NOW(), NULL),
(3, 11, NOW(), NOW(), NULL),
(3, 12, NOW(), NOW(), NULL),

-- ===== Team 4 =====
(4, 13, NOW(), NOW(), NULL),
(4, 14, NOW(), NOW(), NULL),
(4, 15, NOW(), NOW(), NULL),
(4, 16, NOW(), NOW(), NULL),

-- ===== Team 5 =====
(5, 17, NOW(), NOW(), NULL),
(5, 18, NOW(), NOW(), NULL),
(5, 19, NOW(), NOW(), NULL),
(5, 20, NOW(), NOW(), NULL);

INSERT INTO activities (
    customer_id,
    task_id,
    type,
    description,
    timestamp
) VALUES
-- ===== TASK 관련 =====
(1, 1, 101, '새 작업 "댓글 기능 구현"을 생성했습니다.', NOW()),
(2, 2, 101, '새 작업 "댓글 조회 최적화"를 생성했습니다.', NOW()),
(3, 3, 101, '새 작업 "댓글 삭제 처리"를 생성했습니다.', NOW()),

(1, 1, 102, '작업 "댓글 기능 구현" 정보를 수정했습니다.', NOW()),
(2, 2, 102, '작업 "댓글 조회 최적화" 정보를 수정했습니다.', NOW()),
(3, 3, 102, '작업 "댓글 삭제 처리" 정보를 수정했습니다.', NOW()),

(1, 1, 104, '작업 상태를 TODO에서 IN_PROGRESS로 변경했습니다.', NOW()),
(2, 2, 104, '작업 상태를 TODO에서 DONE으로 변경했습니다.', NOW()),
(3, 3, 104, '작업 상태를 IN_PROGRESS에서 DONE으로 변경했습니다.', NOW()),

(1, 1, 103, '작업 "댓글 기능 구현"을 삭제했습니다.', NOW()),
(2, 2, 103, '작업 "댓글 조회 최적화"를 삭제했습니다.', NOW()),

-- ===== COMMENT 생성 =====
(1, 1, 201, '작업 "댓글 기능 구현"에 댓글을 작성했습니다.', NOW()),
(2, 1, 201, '작업 "댓글 기능 구현"에 댓글을 작성했습니다.', NOW()),
(3, 1, 201, '작업 "댓글 기능 구현"에 댓글을 작성했습니다.', NOW()),
(1, 2, 201, '작업 "댓글 조회 최적화"에 댓글을 작성했습니다.', NOW()),
(2, 3, 201, '작업 "댓글 삭제 처리"에 댓글을 작성했습니다.', NOW()),

-- ===== COMMENT 수정 =====
(1, 1, 202, '댓글을 수정했습니다.', NOW()),
(2, 1, 202, '댓글을 수정했습니다.', NOW()),
(3, 1, 202, '댓글을 수정했습니다.', NOW()),
(1, 2, 202, '댓글을 수정했습니다.', NOW()),
(2, 3, 202, '댓글을 수정했습니다.', NOW()),

-- ===== COMMENT 삭제 =====
(1, 1, 203, '댓글을 삭제했습니다.', NOW()),
(2, 1, 203, '댓글을 삭제했습니다.', NOW()),
(3, 1, 203, '댓글을 삭제했습니다.', NOW()),
(1, 2, 203, '댓글을 삭제했습니다.', NOW()),
(2, 3, 203, '댓글을 삭제했습니다.', NOW()),
(3, 2, 203, '댓글을 삭제했습니다.', NOW()),
(1, 3, 203, '댓글을 삭제했습니다.', NOW()),
(2, 2, 203, '댓글을 삭제했습니다.', NOW());
