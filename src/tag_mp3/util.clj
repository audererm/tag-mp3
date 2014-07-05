(ns tag-mp3.util)

(defn mp3?
  [file]
  (.endsWith (.getName file) ".mp3"))

(defn get-mp3s
  "Returns a vector containing all mp3 files within a directory"
  [dir]
  (vec (filter (fn [v] (or (mp3? v) (.isDirectory v))) (vec (.listFiles dir)))))
