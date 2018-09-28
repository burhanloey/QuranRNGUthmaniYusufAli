(ql:quickload :drakma)
(ql:quickload :cl-json)

(defparameter *result* (json:decode-json-from-string
                        (flexi-streams:octets-to-string
                         (drakma:http-request "http://api.alquran.cloud/surah"))))

(defun get-json-value (alist indicator)
  (cdar
   (remove-if-not (lambda (x) (equal (car x) indicator))
                  alist)))

(defparameter *data* (get-json-value *result* :data))

(defparameter *ayahs* (mapcar (lambda (x) (get-json-value x :number-of-ayahs))
                              *data*))

(defun print-number-of-ayahs ()
  (format t "~{~a~^, ~}" *ayahs*))

(defparameter *english-names* (mapcar (lambda (x) (get-json-value x :english-name))
                                      *data*))

(defun print-title-strings ()
  (format t "~:{<string name=\"title_~a\">~a</string>~%~}"
          (loop for i from 1 for name in *english-names*
                collect (list i name))))
