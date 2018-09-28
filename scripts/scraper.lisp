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

(defparameter *english-names*
  (mapcar
   (lambda (surah) (get-json-value surah :english-name))
   *surahs-data*))

(defun print-title-strings ()
  (format t "~:{<string name=\"title_~a\">~a</string>~%~}"
          (loop for i from 1 for name in *english-names*
                collect (list i name))))

(defparameter *quran*
  (json-request "http://api.alquran.cloud/quran/quran-uthmani"))

(defparameter *quran-data*
  (get-json-value *quran* :data))

(defparameter *ayahs*
  (get-json-value *quran-data* :surahs))
