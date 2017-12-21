(ns football-api.models.artists_model
	(:require
		[reagent.core :as reagent :refer [atom]]
		))

(def log (.-log js/console))


(def artists_atom (atom {:name "Default name3"}))

; Private
(defn- on_add_artist [key atom old new] (log new))
(add-watch artists_atom 'ARTISTS-MODEL-ADD-ARTIST-SUCCESS' on_add_artist)


; Public
(defn add_artist [artists] (reset! artists_atom artists))
