(ns football-api.pages.about_page
	(:require
		[reagent.core :as reagent :refer [atom]]
		[football-api.models.artists_model :as artists_model]
		))

(def log (.-log js/console))


(defn render_artist_name []
	(log "render artist name")
	[:h4 "artist name: " (get-in (deref artists_model/artists_atom) [:name])])


(defn render []
	(log "@render About page")
	[:div [:h2 "About page..."]
	 [render_artist_name]
	 [:div [:a {:href "/"} "go to home page"]]])
