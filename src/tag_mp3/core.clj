(ns tag-mp3.core
  (:require [tag-mp3.util :as u]
            [clojure.java.io :as io]
            [clojure.string :as string])
  (:import [org.blinkenlights.jid3 MP3File]
           [org.blinkenlights.jid3.v2 ID3V2_3_0Tag]))

(declare auto-tag tag-dir)

(def music-folder "/home/michael/music/")

(defn auto-tag
  "Default format: NUMBER TITLE - ARTIST" ; FIXME: Custom formats
  [file]
  (println "Tagging "(.getName file))
  (if (.isDirectory file)
    (tag-dir file)
    (let [tags (string/split (.getName file) #"\s+")
          number (read-string (get tags 0))
          artist (get tags 1)
          title (string/replace (get tags 3) ".mp3" "")
          media-file (new MP3File file)
          tag (new ID3V2_3_0Tag)]
      (.setTrackNumber tag number)
      (.setArtist tag artist)
      (.setTitle tag title)
      (.setID3Tag media-file tag)
      (.sync media-file))))

(defn tag-dir
  "Tags an entire directory."
  [dir]
  (if (.isDirectory dir)
    (let [files (u/get-mp3s dir)]
      (doseq [file files] (auto-tag file)))
    (println "ERROR: " (.getName dir) " is not a directory!!")))

(defn -main
  "Tags mp3 files."
  [& args]
  (auto-tag (io/file test-dir))
  (println "Tagging complete."))
