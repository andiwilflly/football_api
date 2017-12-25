(ns football-api.async-handler
	"Asynchronous compojure-api application."
	(:require [compojure.api.sweet :refer :all]
	          [ring.util.http-response :refer :all]

	          [hiccup.page :refer [include-js include-css html5]]
	          [config.core :refer [env]]

	          [manifold.deferred :as d]
	          [clojure.core.async :as async]
	          compojure.api.async))

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



;(interceptors/defbefore takes-time
;	[context]
;	(let [channel (chan)]
;		(go
;		 (let [result (lengthy-computation (:request context))
;		       new-context (assoc context :response (ring-resp/response result))]
;			 (>! channel new-context)))
;		channel))
;
;(defroutes routes
;	[[["/takes-time" {:get takes-time}]]])



(defapi app
	(GET "/" [] (loading-page))

	(GET "/hello" []
	     :query-params [name :- String]
	     (ok {:message (str "Hello, " name)}))

	(GET "/hello-async" []
	     :query-params [name :- String]
	     (async/go

	      (ok {:message "Hello.."}))))