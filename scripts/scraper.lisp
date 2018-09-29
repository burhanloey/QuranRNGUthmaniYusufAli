(ql:quickload :drakma)
(ql:quickload :cl-json)

(defun json-request (url)
  (json:decode-json-from-string
   (flexi-streams:octets-to-string
    (drakma:http-request url))))

(defun get-json-value (alist indicator)
  (cdar
   (remove-if-not  ; filter
    (lambda (x) (equal (car x) indicator))
    alist)))


;; Number of ayahs

(defparameter *surahs*
  (json-request "http://api.alquran.cloud/surah"))

(defparameter *surahs-data*
  (get-json-value *surahs* :data))

(defparameter *number-of-ayahs*
  (mapcar
   (lambda (surah) (get-json-value surah :number-of-ayahs))
   *surahs-data*))

(defun print-number-of-ayahs ()
  (format t "~{~a~^, ~}" *number-of-ayahs*))


;; Surah names in english

(defparameter *english-names*
  (mapcar
   (lambda (surah) (get-json-value surah :english-name))
   *surahs-data*))

(defun print-title-strings ()
  (format t "~:{<string name=\"title_~a\">~a</string>~%~}"
          (loop for i from 1 for name in *english-names*
                collect (list i name))))


;; Quran texts

(defun print-texts (surahs prefix)
  (loop for surah in surahs
        for surah-no = (get-json-value surah :number)
        for ayahs = (get-json-value surah :ayahs)
        do (loop for ayah in ayahs
                 for ayah-no = (get-json-value ayah :number-in-surah)
                 for text = (get-json-value ayah :text)
                 do (format t "<string name=\"~a_~a_~a\">~a</string>~%"
                            prefix surah-no ayah-no text))))

(defparameter *quran*
  (json-request "http://api.alquran.cloud/quran/quran-uthmani"))

(defparameter *quran-data*
  (get-json-value *quran* :data))

(defparameter *surahs*
  (get-json-value *quran-data* :surahs))

(defparameter *en-quran*
  (json-request "http://api.alquran.cloud/quran/en.yusufali"))

(defparameter *en-quran-data*
  (get-json-value *en-quran* :data))

(defparameter *en-surahs*
  (get-json-value *en-quran-data* :surahs))

(format t "File loaded.")
