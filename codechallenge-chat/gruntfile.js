module.exports = function (grunt) {
    grunt.loadNpmTasks('grunt-browserify');
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-jasmine-nodejs');

    grunt.registerTask('default', ['browserify']);
    grunt.registerTask('test', ['karma']);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        browserify: {
            main: {
                src: './public/javascripts/angular-app.js',
                dest: './public/javascripts/main.js'
            }
        },
        karma: {
            unit: {
                options: {
                    frameworks: ['jasmine', 'browserify'],
                    singleRun: true,
                    preprocessors: {
                        './public/javascripts/angular-app.js': ['browserify']
                    },
                    browsers: ['PhantomJS'],
                    files: [
                        './node_modules/angular/angular.js',
                        './node_modules/angular-mocks/angular-mocks.js',
                        './spec/client/*.js',
                        './public/javascripts/main.js'
                    ],
                    plugins: [
                        'karma-phantomjs-launcher',
                        'karma-jasmine',
                        'karma-browserify'
                    ]
                }
            }
        },
        jasmine_nodejs: {
            your_target: {
                specs: [
                    "./spec/server/**"
                ]
            }
        }
    });
};