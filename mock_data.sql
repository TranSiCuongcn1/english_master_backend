-- ==========================================
-- SCRIPT DỮ LIỆU MẪU (MOCK DATA) - ENGLISH MASTER
-- ==========================================
-- Lưu ý: Mật khẩu mặc định: "123456"
-- Hash: $2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK

-- Làm sạch dữ liệu trước khi chèn
TRUNCATE TABLE public.user_answers, public.test_results, public.notifications, public.flashcards, public.question_options, public.questions, public.tests, public.reading_passages, public.grammar_examples, public.grammar_topics, public.vocabulary_words, public.vocabulary_topics, public.users CASCADE;

-- 1. USERS
INSERT INTO public.users (id, name, email, password_hash, role) VALUES 
('11111111-1111-1111-1111-111111111111', 'Admin Super', 'admin@englishmaster.com', '$2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK', 'admin'),
('22222222-2222-2222-2222-222222222222', 'Nguyen Van A', 'user@englishmaster.com', '$2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK', 'user'),
('33333333-3333-3333-3333-333333333333', 'Tran Thi B', 'user2@englishmaster.com', '$2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK', 'user'),
('44444444-4444-4444-4444-444444444444', 'Le Van C', 'user3@englishmaster.com', '$2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK', 'user'),
('55555555-5555-5555-5555-555555555555', 'Pham Thi D', 'user4@englishmaster.com', '$2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK', 'user'),
('66666666-6666-6666-6666-666666666666', 'Hoang Van E', 'user5@englishmaster.com', '$2a$10$fzMZ9db3ko/mDSLPGn8QtO5sGNuYR5WhehUyTP0ZoNNCUitbDpsDK', 'user');

-- 2. VOCABULARY TOPICS
INSERT INTO public.vocabulary_topics (id, title, description, icon_name) VALUES 
('a0000000-0000-0000-0000-000000000001', 'Office & Business', 'Từ vựng giao tiếp công sở', 'briefcase'),
('a0000000-0000-0000-0000-000000000002', 'Travel & Transport', 'Từ vựng du lịch', 'plane'),
('a0000000-0000-0000-0000-000000000003', 'Education', 'Từ vựng về giáo dục và học tập', 'book'),
('a0000000-0000-0000-0000-000000000004', 'Technology', 'Từ vựng về công nghệ thông tin', 'laptop'),
('a0000000-0000-0000-0000-000000000005', 'Health & Medicine', 'Từ vựng y tế và sức khỏe', 'heart'),
('a0000000-0000-0000-0000-000000000006', 'Daily Routine', 'Từ vựng hoạt động thường ngày', 'clock');

-- 3. VOCABULARY WORDS
INSERT INTO public.vocabulary_words (topic_id, word, word_type, meaning, example, phonetics, order_index) VALUES 
('a0000000-0000-0000-0000-000000000001', 'Negotiate', 'verb', 'Đàm phán', 'We need to negotiate the terms.', '/nɪˈɡəʊʃieɪt/', 1),
('a0000000-0000-0000-0000-000000000001', 'Deadline', 'noun', 'Hạn chót', 'The deadline is next Friday.', '/ˈdedlaɪn/', 2),
('a0000000-0000-0000-0000-000000000001', 'Contract', 'noun', 'Hợp đồng', 'Please sign the contract.', '/ˈkɒntrækt/', 3),
('a0000000-0000-0000-0000-000000000002', 'Itinerary', 'noun', 'Lịch trình', 'Here is our travel itinerary.', '/aɪˈtɪnərəri/', 1),
('a0000000-0000-0000-0000-000000000002', 'Accommodation', 'noun', 'Chỗ ở', 'We booked cheap accommodation.', '/əˌkɒməˈdeɪʃn/', 2),
('a0000000-0000-0000-0000-000000000003', 'Assignment', 'noun', 'Bài tập', 'I finished my assignment.', '/əˈsaɪnmənt/', 1),
('a0000000-0000-0000-0000-000000000003', 'Curriculum', 'noun', 'Chương trình học', 'The school curriculum is challenging.', '/kəˈrɪkjələm/', 2),
('a0000000-0000-0000-0000-000000000004', 'Database', 'noun', 'Cơ sở dữ liệu', 'The database was updated yesterday.', '/ˈdeɪtəbeɪs/', 1),
('a0000000-0000-0000-0000-000000000004', 'Algorithm', 'noun', 'Thuật toán', 'This search algorithm is very fast.', '/ˈælɡərɪðəm/', 2);

-- 4. GRAMMAR TOPICS
INSERT INTO public.grammar_topics (id, title, content, order_index) VALUES 
('b0000000-0000-0000-0000-000000000001', 'Present Simple', 'S + V(s/es) - Dùng để diễn tả sự thật hiển nhiên hoặc thói quen.', 1),
('b0000000-0000-0000-0000-000000000002', 'Past Continuous', 'S + was/were + V-ing - Hành động đang xảy ra tại một thời điểm trong quá khứ.', 2),
('b0000000-0000-0000-0000-000000000003', 'Present Perfect', 'S + have/has + V3/ed - Hành động bắt đầu trong quá khứ, kéo dài đến hiện tại.', 3),
('b0000000-0000-0000-0000-000000000004', 'Conditional Sentences (Type 1)', 'If + S + V(s/es), S + will/can/may + V_inf', 4);

-- 5. TESTS
INSERT INTO public.tests (id, title, description, category, year, duration_minutes, total_questions) VALUES 
('c0000000-0000-0000-0000-000000000001', 'TOEIC ETS 2023 - Test 1', 'Đề thi chuẩn format ETS 2023 - Reading.', 'TOEIC', 2023, 120, 100),
('c0000000-0000-0000-0000-000000000002', 'IELTS Mock Test 1', 'Đề thi thử IELTS Academic Reading.', 'IELTS', 2023, 60, 40),
('c0000000-0000-0000-0000-000000000003', 'Grammar Mini Test', 'Bài tập ngữ pháp tổng hợp.', 'GRAMMAR', 2024, 30, 20),
('c0000000-0000-0000-0000-000000000004', 'Vocabulary Basic', 'Bài kiểm tra từ vựng trình độ A1-A2.', 'VOCABULARY', 2024, 15, 15);

-- 6. QUESTIONS & OPTIONS
INSERT INTO public.questions (id, test_id, type, text, correct_answer, skill, order_index) VALUES 
('e0000000-0000-0000-0000-000000000001', 'c0000000-0000-0000-0000-000000000001', 'multiple-choice', 'Mr. Smith is ______ responsible for the marketing campaign.', 'entirely', 'GRAMMAR', 1),
('e0000000-0000-0000-0000-000000000002', 'c0000000-0000-0000-0000-000000000001', 'multiple-choice', 'If I had known you were coming, I ______ a cake.', 'would have baked', 'GRAMMAR', 2),
('e0000000-0000-0000-0000-000000000003', 'c0000000-0000-0000-0000-000000000001', 'multiple-choice', 'The new software update will be ______ next Monday.', 'released', 'VOCABULARY', 3),
('e0000000-0000-0000-0000-000000000004', 'c0000000-0000-0000-0000-000000000002', 'multiple-choice', 'The primary purpose of the passage is to ______.', 'inform', 'READING', 1),
('e0000000-0000-0000-0000-000000000005', 'c0000000-0000-0000-0000-000000000003', 'multiple-choice', 'She ______ English for 5 years.', 'has been studying', 'GRAMMAR', 1),
('e0000000-0000-0000-0000-000000000006', 'c0000000-0000-0000-0000-000000000004', 'multiple-choice', 'Choose the synonym for "beautiful".', 'gorgeous', 'VOCABULARY', 1);

INSERT INTO public.question_options (question_id, option_text, order_index) VALUES 
('e0000000-0000-0000-0000-000000000001', 'entire', 1),
('e0000000-0000-0000-0000-000000000001', 'entirety', 2),
('e0000000-0000-0000-0000-000000000001', 'entirely', 3),
('e0000000-0000-0000-0000-000000000001', 'entires', 4),

('e0000000-0000-0000-0000-000000000002', 'baked', 1),
('e0000000-0000-0000-0000-000000000002', 'will bake', 2),
('e0000000-0000-0000-0000-000000000002', 'would bake', 3),
('e0000000-0000-0000-0000-000000000002', 'would have baked', 4),

('e0000000-0000-0000-0000-000000000003', 'release', 1),
('e0000000-0000-0000-0000-000000000003', 'releases', 2),
('e0000000-0000-0000-0000-000000000003', 'released', 3),
('e0000000-0000-0000-0000-000000000003', 'releasing', 4),

('e0000000-0000-0000-0000-000000000004', 'criticize', 1),
('e0000000-0000-0000-0000-000000000004', 'inform', 2),
('e0000000-0000-0000-0000-000000000004', 'entertain', 3),
('e0000000-0000-0000-0000-000000000004', 'persuade', 4),

('e0000000-0000-0000-0000-000000000005', 'studies', 1),
('e0000000-0000-0000-0000-000000000005', 'studied', 2),
('e0000000-0000-0000-0000-000000000005', 'has been studying', 3),
('e0000000-0000-0000-0000-000000000005', 'is studying', 4),

('e0000000-0000-0000-0000-000000000006', 'ugly', 1),
('e0000000-0000-0000-0000-000000000006', 'terrible', 2),
('e0000000-0000-0000-0000-000000000006', 'gorgeous', 3),
('e0000000-0000-0000-0000-000000000006', 'boring', 4);
