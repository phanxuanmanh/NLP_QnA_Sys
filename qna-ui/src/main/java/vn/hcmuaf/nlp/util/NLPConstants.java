package vn.hcmuaf.nlp.util;

public class NLPConstants {
	public static final String QNA_SERVICE_PREFIX = "/qnas";
	public static final String QNA_SERVICE_GET_ANSWER = "answer/getByquestionId";
	public static final String QNA_SERVICE_GET_QUESTION = "question/getByquestionId";
	public static final String LOGIN_SERVICE_PREFIX = "/login/";
	public static final String USER_SERVICE_PREFIX = "/user";
	public static final String USER_SERVICE_GET_BY_ID = "getById";
	public static final String USER_SERVICE_GET_BY_EMAIL = "getByEmail";
	public static final String USER_ADD_SERVICE_POSTFIX = "/user/add";
	public static final String HISTORY_ADD_SERVICE_POSTFIX = "/history/add/";
	public static final String HISTORY_DELETE_SERVICE_POSTFIX = "/history/delete/";
	public static final String HISTORY_GET_BY_USER_SERVICE_POSTFIX = "/history/getQnAByUser";
	public static final String COMMON_QUESTION_SERVICE_POSTFIX = "/history/common/";
	public static final String RECENT_HISTORY_SERVICE_POSTFIX = "/manage/recent";
	public static final String UNANSWED_HISTORY_SERVICE_POSTFIX = "/manage/unanswer";
	public static final String ANSWER_QUESTION_SERVICE_POSTFIX = "/manage/answer/add/";
	public static final String QNA_CORE_WS = "/qna-core-ws";
	public static final int SERVICE_PORT = 8080;
	public static final String SERVICE_HOST = "localhost";
	public static final String HTTP = "http";
	public static final String QNA_MENU = "qna-menu";
	public static final String LIST_QNA_PANEL_SIZE = "500px";
	public static final String MAIN_LAYOUT_NAME = "main-layout";

	public static final String MAIN_LAYOUT_LOCATION_MENUBAR = "menubar";

	public static final String MAIN_LAYOUT_LOCATION_MAIN = "main";

	public static final String STYLE_LABEL_CUSTOM = "label-custom";

	public static final String STYLE_HORIZONTAL_CUSTOM = "custom-horizontal";

	public static final String STYLE_QUESTION_PANEL_CUSTOM = "question-custom";

	public static final String BTN_ALIGN_LEFT = "btn-align-left";

	public static final String[] FEEDBACK_TYPES = { "Có", "Trả lời sai chủ đề", "Không phải câu trả lời tôi muốn" };

	public static final String[] FEEDBACK_ACTIONS = { "Có", "Không" };

	public static final String[] STAR_CAPTIONS = { "Không tốt", "Tạm được", "Bình thường", "Tốt", "Xuất sắc" };

	public static final String[] TOPICS = { "Tất cả", "Giáo dục", "Điểm", "Tuyển sinh" };

	public static final Long[] RESULT_SIZES = { 5l, 10l, 15l, 20l, 50l, 100l };

	public static final double RATING_START_INIT = 3d;

	public static final int RATING_STAR_MAX = 5;
}
