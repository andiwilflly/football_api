(ns football-api.handler
  (:require
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ajax.core :as ajax]
            [hiccup.page :refer [include-js include-css html5]]
            [football_api.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
  ))


(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])


(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])


(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(defn handler [resp] (response {
	:status 200
    :content-type "application/json; charset=UTF-8"
	:artists "??" }))

(defn artists-top [request]
	(response {:artists "[ARTISTS]" }))


(defroutes routes
    (GET "/" [] (loading-page))
    (GET "/about" [] (loading-page))
	(GET "/user/:userId" [userId] (loading-page))
    (GET "/artists/top" [] (wrap-json-response artists-top))

    (resources "/")
    (not-found "Not Found"))

(def app (wrap-middleware #'routes))
