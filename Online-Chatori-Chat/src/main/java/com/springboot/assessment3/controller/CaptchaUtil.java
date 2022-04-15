package com.springboot.assessment3.controller;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

public class CaptchaUtil {

	//Creating Captcha Object
		public static Captcha createCaptcha(int width, int height) {
			
			return new Captcha.Builder(width, height)
					.addBackground(new GradiatedBackgroundProducer())
					.addText(new DefaultTextProducer())
					.addNoise(new CurvedLineNoiseProducer())
					.build();
		}
		
		//Converting to binary String
		public static String encodeCaptcha(Captcha captcha) {
			String image = null;
			try {
				ByteArrayOutputStream os= new ByteArrayOutputStream();
				ImageIO.write(captcha.getImage(),"png", os);
				byte[] byteArray= Base64.getEncoder().encode(os.toByteArray());
				image = new String(byteArray);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
	}
