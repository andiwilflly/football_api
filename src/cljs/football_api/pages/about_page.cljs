(ns football-api.pages.about_page
	(:require
		[reagent.core :as reagent :refer [atom]]
		[football-api.models.artists-model :as artists-model]
		))

(def log (.-log js/console))


(defn render_artist_name []
	(log "render artist name")
	[:h4 "artist name: " (:name (deref artists-model/artists_atom))])


(defn render []
	(log "@render About page")
	[:div [:h2 "Artist page"]
	 [render_artist_name]
	 [:div [:a {:href "/"} "go to home page"]]])
